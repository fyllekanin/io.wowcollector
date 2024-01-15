import { createSSRApp } from 'vue';
import { renderToString } from '@vue/server-renderer';
import express from 'express';
import App from './App.vue';

const port = process.env.PORT ?? 3000;

const server = express();

server.get('*', async (_, res) => {
  const app = createSSRApp(App);

  try {
    const appContent = await renderToString(app);
    const html = `
      <!DOCTYPE html>
      <html lang="en">
        <head>
          <title>wowcollector</title>
        </head>
        <body>
          <div id="app">${appContent}</div>
        </body>
      </html>
    `;

    res.end(html);
  } catch (error) {
    console.error(error);
    res.status(500).send('Internal Server Error');
  }
});

server.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});
