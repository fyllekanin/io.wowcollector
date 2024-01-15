import { createApp } from 'vue';

// Plugins
import { createPinia } from 'pinia';
import { createVuetify } from 'vuetify';
import { isMobile, isTablet, isSmallTablet } from './plugins';

import '@mdi/font/css/materialdesignicons.css';
import { aliases, mdi } from 'vuetify/iconsets/mdi';
import 'vuetify/styles';
import * as components from 'vuetify/components';
import * as directives from 'vuetify/directives';

import { customTheme } from '@/assets/theme';
import { customSVGs } from '@/utils/customSvgs';

const vuetify = createVuetify({
  components,
  directives,
  icons: {
    defaultSet: 'mdi',
    aliases,
    sets: {
      mdi,
      custom: customSVGs,
    },
  },
  theme: {
    defaultTheme: 'customTheme',
    themes: {
      customTheme,
    },
  },
});
const pinia = createPinia();

import App from './App.vue';
import router from './router';

const app = createApp(App);

app
  .use(pinia)
  .use(isMobile)
  .use(isTablet)
  .use(isSmallTablet)
  .use(router)
  .use(vuetify)
  .mount('#app');
