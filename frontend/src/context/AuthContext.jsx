import React, { createContext, useContext, useState, useEffect } from 'react';
import authService from '../services/authService';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    const savedUser = localStorage.getItem('ayurvana_user');
    return savedUser ? JSON.parse(savedUser) : null;
  });
  const [token, setToken] = useState(() => localStorage.getItem('ayurvana_token'));
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (token) {
      authService.getCurrentUser()
        .then((res) => {
          setUser(res.data);
          localStorage.setItem('ayurvana_user', JSON.stringify(res.data));
        })
        .catch(() => {
          logout();
        })
        .finally(() => setLoading(false));
    } else {
      setLoading(false);
    }
  }, [token]);

  const login = async (email, password) => {
    const res = await authService.login(email, password);
    const data = res.data;
    localStorage.setItem('ayurvana_token', data.accessToken);
    const userProfile = {
      id: data.userId,
      email: data.email,
      fullName: data.fullName,
      roles: data.roles,
    };
    localStorage.setItem('ayurvana_user', JSON.stringify(userProfile));
    setToken(data.accessToken);
    setUser(userProfile);
    return data;
  };

  const register = async (fullName, email, password, phoneNumber, role) => {
    const res = await authService.register(fullName, email, password, phoneNumber, role);
    const data = res.data;
    localStorage.setItem('ayurvana_token', data.accessToken);
    const userProfile = {
      id: data.userId,
      email: data.email,
      fullName: data.fullName,
      roles: data.roles,
    };
    localStorage.setItem('ayurvana_user', JSON.stringify(userProfile));
    setToken(data.accessToken);
    setUser(userProfile);
    return data;
  };

  const logout = () => {
    localStorage.removeItem('ayurvana_token');
    localStorage.removeItem('ayurvana_user');
    setToken(null);
    setUser(null);
  };

  const hasRole = (roleName) => {
    if (!user || !user.roles) return false;
    const formatted = roleName.startsWith('ROLE_') ? roleName : `ROLE_${roleName}`;
    return user.roles.includes(formatted);
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        token,
        loading,
        isAuthenticated: !!token && !!user,
        login,
        register,
        logout,
        hasRole,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}

export default AuthContext;
