/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'primary': 'rgb(22 163 74)',
        'primary-light': 'rgb(134 239 172)'
      },
    },
  },
  plugins: [
    '@tailwindcss/forms',
  ],
}

