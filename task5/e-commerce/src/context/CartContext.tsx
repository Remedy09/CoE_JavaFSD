import React, { createContext, useContext, useState } from "react";

// Define types for better TypeScript support
interface CartContextType {
  cart: { [key: number]: number };
  addToCart: (id: number) => void;
  removeFromCart: (id: number) => void;
}

const CartContext = createContext<CartContextType | undefined>(undefined);

export const CartProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [cart, setCart] = useState<{ [key: number]: number }>({});

  const addToCart = (id: number) => {
    setCart((prev) => ({
      ...prev,
      [id]: (prev[id] || 0) + 1,
    }));
  };

  const removeFromCart = (id: number) => {
    setCart((prev) => {
      if (!prev[id]) return prev;
      const newCart = { ...prev };
      if (newCart[id] === 1) {
        delete newCart[id]; // Remove from cart if quantity reaches 0
      } else {
        newCart[id] -= 1;
      }
      return newCart;
    });
  };

  return (
    <CartContext.Provider value={{ cart, addToCart, removeFromCart }}>
      {children}
    </CartContext.Provider>
  );
};

export const useCart = () => {
  const context = useContext(CartContext);
  if (!context) {
    throw new Error("useCart must be used within a CartProvider");
  }
  return context;
};
