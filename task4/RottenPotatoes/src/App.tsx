import { useState } from "react";
import "./App.css";
import logo from "./logo.png";
function App() {
  const [currentSlide, setCurrentSlide] = useState(0);
  const [reviews, setReviews] = useState([]);
  const [movieName, setMovieName] = useState("");
  const [username, setUsername] = useState("");
  const [rating, setRating] = useState("");

  const handleReviewSubmit = (e) => {
    e.preventDefault();
    if (movieName && username && rating) {
      const newReview = { movieName, username, rating };
      setReviews([...reviews, newReview]);
      setMovieName("");
      setUsername("");
      setRating("");
    }
  };

  const slides = [
    {
      title: "REACHER: S3 FIRST REVIEWS",
      description: "Action-packed and relentlessly entertaining",
      image: "https://images.justwatch.com/poster/305778641/s592/season-3.webp",
    },
    {
      title: "2025 OSCARS BALLOT",
      description: "With Tomatometer and Popcornmeter scores for all noms!",
      image:
        "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEi9Ov9O4iSeeR3lkjBeyynmR5oj57pjGznOpCoVLR8CiuZl1b8-Xh9FZQua6ibN3RVldpEra_p5R0lKwTIyRZScRxm-hZWsOEEZzCnfNSa7ZABEeHDu-kwmiX-NQ0H74E0OFe_XbsMxHYuMAOmGItnlFhHBEH63UyIyE3DochxtOKuQ-_4T5fS3bliNfkT4/s1200/Oscars2025-Shortlists-crop.jpg",
    },
  ];

  const movies = [
    {
      title: "Last Breath",
      image:
        "https://m.media-amazon.com/images/M/MV5BY2Q3MjQ3ZjMtMmQ3My00MTNlLWI5NzEtYzJjMTE3OWQyNjQ3XkEyXkFqcGc@._V1_.jpg",
      criticScore: "89",
      audienceScore: "70",
    },
    {
      title: "Smile",
      image:
        "https://m.media-amazon.com/images/M/MV5BZjE2ZWIwMWEtNGFlMy00ZjYzLWEzOWEtYzQ0MDAwZDRhYzNjXkEyXkFqcGdeQXVyMTUzMTg2ODkz._V1_.jpg",
      criticScore: "77",
      audienceScore: "62",
    },
    {
      title: "Captain India",
      image:
        "https://assetscdn1.paytm.com/images/cinema/captain1-d5173e50-8c2d-11ec-a324-23682b4f5e45.jpg",
      criticScore: "49",
      audienceScore: "80",
    },
    {
      title: "Paddington in Peru",
      image:
        "https://m.media-amazon.com/images/I/91kF7jWoRXL._AC_UF1000,1000_QL80_.jpg",
      criticScore: "94",
      audienceScore: "91",
    },
  ];

  return (
    <div className="app">
      <header className="header">
        <div className="logo">
          <img
            src= { logo }
            style={{ width: "120px" , height: "120px"}} 
            alt="Rotten Potatoes"
          />
        </div>
        <div className="search-bar">
          <input type="text" placeholder="Search" />
        </div>
        <nav className="main-nav">
          <a href="#">MOVIES</a>
          <a href="#">TV SHOWS</a>
          <a href="#">FANSTORE</a>
          <a href="#">NEWS</a>
          <a href="#">SHOWTIMES</a>
        </nav>
      </header>

      <div className="trending-bar">
        <span>TRENDING ON RT</span>
        <a href="#">Awards Leaderboard</a>
        <a href="#">Rotten Tomatoes Awards</a>
        <a href="#">Best New Horror Movies</a>
        <a href="#">TV Premiere Dates</a>
      </div>

      <div className="carousel">
        <div
          className="carousel-background"
          style={{ backgroundImage: `url(${slides[currentSlide].image})` }}
        ></div>
        <div className="carousel-overlay"></div>
        <button
          className="carousel-button prev"
          onClick={() =>
            setCurrentSlide((prev) =>
              prev === 0 ? slides.length - 1 : prev - 1
            )
          }
        >
          ‹
        </button>

        <div className="carousel-content">
          <h2>{slides[currentSlide].title}</h2>
          <p>{slides[currentSlide].description}</p>
        </div>

        <button
          className="carousel-button next"
          onClick={() =>
            setCurrentSlide((prev) =>
              prev === slides.length - 1 ? 0 : prev + 1
            )
          }
        >
          ›
        </button>

        <div className="carousel-dots">
          {slides.map((_, index) => (
            <button
              key={index}
              className={`dot ${currentSlide === index ? "active" : ""}`}
              onClick={() => setCurrentSlide(index)}
            />
          ))}
        </div>
      </div>

      <section className="now-playing">
        <div className="section-header">
          <h2>Now in Theaters</h2>
          <span>Brought to you by Fandango</span>
          <a href="#" className="view-all">
            VIEW ALL
          </a>
        </div>

        <div className="movie-grid">
          {movies.map((movie, index) => (
            <div key={index} className="movie-card">
              <div className="movie-poster">
                <img src={movie.image} alt={movie.title} />
                <button className="play-button">▶</button>
              </div>
              <div className="movie-scores">
                <span className="critic-score">{movie.criticScore}%</span>
                <span className="audience-score">{movie.audienceScore}%</span>
              </div>
              <h3>{movie.title}</h3>
              <button className="watchlist-button">+ WATCHLIST</button>
            </div>
          ))}
        </div>
      </section>

      <section className="review-section">
        <h2>Submit Your Movie Review</h2>
        <form onSubmit={handleReviewSubmit} className="review-form">
          <input
            type="text"
            placeholder="Movie Name"
            value={movieName}
            onChange={(e) => setMovieName(e.target.value)}
            required
          />
          <input
            type="text"
            placeholder="Your Name"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
          <input
            type="number"
            placeholder="Rating (1-5)"
            value={rating}
            onChange={(e) => setRating(e.target.value)}
            min="1"
            max="5"
            required
          />
          <button type="submit">Submit Review</button>
        </form>

        {/* Display Reviews */}
        <div className="reviews-list">
          <h3>User Reviews</h3>
          {reviews.length === 0 ? <p>No reviews yet.</p> : 
            reviews.map((review, index) => (
              <div key={index} className="review-card">
                <h4>{review.movieName}</h4>
                <p><strong>{review.username}</strong>: {review.rating}⭐</p>
              </div>
            ))
          }
        </div>
      </section>

    </div>
  );
}

export default App;
