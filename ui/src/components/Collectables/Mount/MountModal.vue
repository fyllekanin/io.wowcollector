<script setup lang="ts">
import type { MountInformation } from '@/types/collections/mounts';
import type { TooltipInfo } from '@/types/misc';

import { watch, ref, inject } from 'vue';
import { parseMountInfo } from '@/utils';

const dialog = ref(false);
const tab = ref(null);
const mount = ref<MountInformation>({
  name: '',
  description: '',
  creatureDisplay: '',
  id: 0,
  isCollected: false,
  icon: '',
});
const mountDetails = ref<TooltipInfo>({} as TooltipInfo);

const isSmallTablet = inject('$isSmallTablet');

defineExpose({
  open(clickedMount: MountInformation, detailedMountInformation: { tooltip: string }) {
    mount.value = clickedMount;

    mountDetails.value = parseMountInfo(detailedMountInformation.tooltip);

    dialog.value = true;
  },
  close() {
    dialog.value = false;
  },
});

watch(tab, (newVal, oldVal) => {
  if (newVal === 2) {
    window.open(`https://wowhead.com/mount/${mount.value.id}`, '_blank');
    tab.value = oldVal;
  }
});
</script>

<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" fullscreen :scrim="false" transition="dialog-bottom-transition">
      <v-card>
        <v-toolbar color="primary">
          <v-btn icon dark @click="dialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>
        <v-card-title class="card-title">
          <section class="top">
            <header>
              <h1>{{ mount.name }}</h1>
              <v-img :src="mount.creatureDisplay" :alt="mount.name" width="300px"></v-img>
            </header>
          </section>
        </v-card-title>
        <v-divider></v-divider>
        <v-card-text class="card-content">
          <v-tabs v-model="tab" color="primary" grow show-arrows>
            <v-tab :value="0">Description</v-tab>
            <v-tab :value="1">Mount Deatils</v-tab>
            <v-tab :value="2" append-icon="mdi-open-in-new">wowhead</v-tab>
          </v-tabs>
          <v-window class="window-content" v-model="tab">
            <v-window-item :value="0">
              <v-container fluid :class="isSmallTablet ? '' : 'ma-10'">
                <v-row>
                  <v-col cols="12" md="12">
                    <h2>Mount Information</h2>
                    <v-table>
                      <tbody>
                        <tr>
                          <td>Name:</td>
                          <td>{{ mount.name }}</td>
                        </tr>
                        <tr>
                          <td>Description:</td>
                          <td>{{ mount.description }}</td>
                        </tr>
                        <tr>
                          <td>Collected:</td>
                          <td>
                            <v-icon
                              class="collected-icon"
                              :icon="mount.isCollected ? 'mdi-check' : 'mdi-close'"
                              :color="mount.isCollected ? 'green' : 'red'"
                            />
                          </td>
                        </tr>
                      </tbody>
                    </v-table>
                  </v-col>
                </v-row>
              </v-container>
            </v-window-item>
            <v-window-item :value="1" :class="isSmallTablet ? '' : 'ma-10'">
              <v-table>
                <thead>
                  <tr>
                    <th class="text-left">Details</th>
                    <th class="text-left">Value</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="([key, value], index) in Object.entries(mountDetails)" :key="index">
                    <td>{{ key }}</td>
                    <td>{{ value }}</td>
                  </tr>
                </tbody>
              </v-table>
            </v-window-item>
          </v-window>
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<style scoped lang="scss">
header {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  max-width: 100%;
}
h1 {
  white-space: pre-wrap;
  word-break: keep-all;
}

.top,
.bottom {
  display: flex;
  justify-content: space-between;
  padding: 1rem;
}

.top {
  justify-content: center;
}

.card-title {
  padding-bottom: 0 !important;
}

.card-content {
  padding-top: 0 !important;

  @media (max-width: 600px) {
    padding: 0 !important;
  }
}

.window-content {
  @media (max-width: 600px) {
    padding: 1rem !important;
  }
  .v-list-item {
    padding: 0 !important;
  }
}

.collected-icon {
  margin-bottom: 5px;
}
</style>
