/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        ayur: {
          50: '#f4f7f4',
          100: '#e5ede5',
          200: '#cbdccb',
          300: '#a3c2a4',
          400: '#75a277',
          500: '#528555',
          600: '#3e6b41',
          700: '#325535',
          800: '#2a442d',
          900: '#233826',
          950: '#111e13',
        },
        earth: {
          50: '#faf7f2',
          100: '#f3ece0',
          200: '#e5d7be',
          300: '#d5bd97',
          400: '#c4a071',
          500: '#b68853',
          600: '#a37145',
          700: '#87593a',
          800: '#6f4833',
          900: '#5b3c2d',
        },
        gold: {
          400: '#f4c430',
          500: '#d4af37',
          600: '#b8972e',
        }
      },
      fontFamily: {
        sans: ['Inter', 'system-ui', 'sans-serif'],
        serif: ['Playfair Display', 'Georgia', 'serif'],
      }
    },
  },
  plugins: [],
}
