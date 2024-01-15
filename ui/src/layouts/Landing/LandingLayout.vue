<script lang="ts" setup>
import { getTabs } from '@/constants';
import { goTo, upperCaseFirst } from '@/utils';
</script>

<template>
  <v-divider color="text" class="divider"></v-divider>
  <v-container class="container">
    <v-row>
      <v-col
        cols="12"
        md="6"
        sm="6"
        v-for="(tab, index) in getTabs()
          .filter((tab) => tab.show || tab.isMenu)
          .flatMap((tab) => tab.children || tab)"
        :key="index"
      >
        <v-card v-if="!tab.isMenu" class="card" @click="goTo(tab.route)">
          <v-card-title class="label">
            <v-icon :icon="tab.icon" />
            {{ upperCaseFirst(tab.alias) }}
          </v-card-title>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<style lang="scss" scoped src="./LandingLayout.scss"></style>
