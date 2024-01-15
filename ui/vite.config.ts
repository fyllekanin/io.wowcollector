import { fileURLToPath, URL } from 'node:url';

import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: '@import "@/assets/base.scss";',
      },
    },
  },
  define: {
    'process.env': process.env,
  },
  server: {
    proxy: {
      '^/api/*': {
        target: 'http://127.0.0.1:8000', // Replace with your target API endpoint
        changeOrigin: true,
      },
    },
  },
});
