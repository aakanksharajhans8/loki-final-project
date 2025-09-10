import React, { useState } from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import Login from "./pages/Login";
import Signup from "./pages/Signup";
import ForgotPassword from "./pages/ForgotPassword";
import ResetPassword from "./pages/ResetPassword";

const App = () => {
  // Authenticated user state (simple)
  const [authUser, setAuthUser] = useState(null);

  return (
    <BrowserRouter>
      <Routes>
        {/* Public routes */}
        <Route path="/login" element={<Login setAuthUser={setAuthUser} />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/reset-password" element={<ResetPassword />} />

        {/* Example protected route (profile) */}
        <Route
          path="/profile"
          element={
            authUser ? (
              <div className="min-h-screen flex items-center justify-center text-3xl">
                Welcome, {authUser.firstName}!
              </div>
            ) : (
              <Navigate to="/login" replace />
            )
          }
        />

        {/* Redirect all unknown routes to login */}
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
