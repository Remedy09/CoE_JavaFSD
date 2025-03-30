import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp();
  runApp(ArtExplorerApp());
}

class ArtExplorerApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Art Explorer',
      theme: ThemeData(primarySwatch: Colors.deepPurple),
      home: ArtHomePage(),
      routes: {'/favorites': (context) => FavoritesScreen()},
    );
  }
}

class ArtHomePage extends StatefulWidget {
  @override
  _ArtHomePageState createState() => _ArtHomePageState();
}

class _ArtHomePageState extends State<ArtHomePage> {
  List<dynamic> artList = [];
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    fetchArtData();
  }

  Future<void> fetchArtData() async {
    try {
      final response = await http.get(
        Uri.parse('https://api.artic.edu/api/v1/artworks?limit=20'),
      );
      if (response.statusCode == 200) {
        final rawJson = json.decode(response.body);
        final artworks = rawJson['data'] ?? [];
        final config = rawJson['config'] ?? {};
        final iiifUrl = config['iiif_url'] ?? '';

        setState(() {
          artList =
              artworks.map((artItem) {
                final imageId = artItem['image_id'];
                return {
                  'id': artItem['id'],
                  'title': artItem['title'] ?? 'Untitled',
                  'image_url':
                      imageId != null
                          ? '$iiifUrl/$imageId/full/843,/0/default.jpg'
                          : '',
                };
              }).toList();
          isLoading = false;
        });
      }
    } catch (e) {
      setState(() => isLoading = false);
      print('Error: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Art Explorer'),
        actions: [
          IconButton(
            icon: Icon(Icons.favorite),
            onPressed: () {
              Navigator.pushNamed(context, '/favorites');
            },
          ),
        ],
      ),
      body:
          isLoading
              ? Center(child: CircularProgressIndicator())
              : GridView.builder(
                padding: EdgeInsets.all(10),
                gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                  crossAxisCount: 2,
                  childAspectRatio: 0.75,
                  crossAxisSpacing: 10,
                  mainAxisSpacing: 10,
                ),
                itemCount: artList.length,
                itemBuilder: (context, index) {
                  final artItem = artList[index];
                  return GestureDetector(
                    onTap:
                        () => Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder:
                                (context) => ArtDetailPage(artItem: artItem),
                          ),
                        ),
                    child: Card(
                      elevation: 5,
                      child: Column(
                        children: [
                          Expanded(
                            child:
                                artItem['image_url'] != ''
                                    ? Image.network(
                                      artItem['image_url'],
                                      fit: BoxFit.cover,
                                      width: double.infinity,
                                    )
                                    : Center(child: Text('No image available')),
                          ),
                          Padding(
                            padding: const EdgeInsets.all(8.0),
                            child: Text(
                              artItem['title'] ?? 'Untitled',
                              style: TextStyle(fontWeight: FontWeight.bold),
                            ),
                          ),
                        ],
                      ),
                    ),
                  );
                },
              ),
    );
  }
}

class ArtDetailPage extends StatefulWidget {
  final dynamic artItem;
  ArtDetailPage({required this.artItem});

  @override
  _ArtDetailPageState createState() => _ArtDetailPageState();
}

class _ArtDetailPageState extends State<ArtDetailPage> {
  bool isFavorite = false;

  @override
  void initState() {
    super.initState();
    checkFavoriteStatus();
  }

  Future<void> checkFavoriteStatus() async {
    final doc =
        await FirebaseFirestore.instance
            .collection('favorites')
            .doc(widget.artItem['id'].toString())
            .get();
    setState(() {
      isFavorite = doc.exists;
    });
  }

  Future<void> toggleFavorite() async {
    final docId = widget.artItem['id'].toString();
    setState(() {
      isFavorite = !isFavorite;
    });

    if (isFavorite) {
      await FirebaseFirestore.instance
          .collection('favorites')
          .doc(docId)
          .set(widget.artItem);
    } else {
      await FirebaseFirestore.instance
          .collection('favorites')
          .doc(docId)
          .delete();
    }
  }

  @override
  Widget build(BuildContext context) {
    final artItem = widget.artItem;
    return Scaffold(
      appBar: AppBar(title: Text(artItem['title'] ?? 'Art Detail')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            artItem['image_url'] != ''
                ? Image.network(artItem['image_url'])
                : Text('No image available'),
            SizedBox(height: 10),
            GestureDetector(
              onTap: toggleFavorite,
              child: Icon(
                isFavorite ? Icons.favorite : Icons.favorite_border,
                color: Colors.red,
                size: 30,
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class FavoritesScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Favorites')),
      body: StreamBuilder<QuerySnapshot>(
        stream: FirebaseFirestore.instance.collection('favorites').snapshots(),
        builder: (context, snapshot) {
          if (snapshot.hasError) {
            return Center(child: Text('Error loading favorites.'));
          }
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator());
          }
          final data = snapshot.data;
          if (data == null || data.docs.isEmpty) {
            return Center(child: Text('No favorites yet.'));
          }
          return GridView.builder(
            padding: EdgeInsets.all(10),
            gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
              crossAxisCount: 2,
              childAspectRatio: 0.75,
              crossAxisSpacing: 10,
              mainAxisSpacing: 10,
            ),
            itemCount: data.docs.length,
            itemBuilder: (context, index) {
              final artItem = data.docs[index].data() as Map<String, dynamic>;
              return GestureDetector(
                onTap: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (context) => ArtDetailPage(artItem: artItem),
                    ),
                  );
                },
                child: Card(
                  elevation: 5,
                  child: Column(
                    children: [
                      Expanded(
                        child:
                            artItem['image_url'] != ''
                                ? Image.network(
                                  artItem['image_url'],
                                  fit: BoxFit.cover,
                                  width: double.infinity,
                                )
                                : Center(child: Text('No image available')),
                      ),
                      Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Text(
                          artItem['title'] ?? 'Untitled',
                          style: TextStyle(fontWeight: FontWeight.bold),
                        ),
                      ),
                    ],
                  ),
                ),
              );
            },
          );
        },
      ),
    );
  }
}
