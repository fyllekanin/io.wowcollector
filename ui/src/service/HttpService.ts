import type { AxiosError, AxiosResponse } from 'axios';
import axios from 'axios';

import type { HttpParamater } from '@/types/http';
import { LOCAL_STORAGE } from '@/constants/storage';

export const http = () => {
  const axiosInstance = axios.create({
    baseURL: import.meta.env.VITE_BASE_URL,
  });

  axiosInstance.interceptors.response.use(
    (response: AxiosResponse) => response,
    (error: AxiosError) => Promise.reject(error),
  );

  axiosInstance.interceptors.request.use(
    (config) => {
      if (!config.url?.startsWith('/api/v1')) {
        return config;
      }

      const auth = localStorage.getItem(LOCAL_STORAGE.AUTH);
      if (!auth) {
        return config;
      }

      const { accessToken } = JSON.parse(auth);
      if (!accessToken) {
        return config;
      }

      config.headers.Authorization = `Bearer ${accessToken}`;

      return config;
    },
    (error) => Promise.reject(error),
  );

  return axiosInstance;
};

export const get = async <T>({
  url,
  config,
}: HttpParamater<unknown>): Promise<AxiosResponse<T>> => {
  return http().get(url, config);
};

export const patch = async <B, T>({
  url,
  body,
  config,
}: HttpParamater<B>): Promise<AxiosResponse<T>> => {
  return http().patch(url, body, config);
};

export const post = async <B, T>({
  url,
  body,
  config,
}: HttpParamater<B>): Promise<AxiosResponse<T>> => {
  return http().post(url, body, config);
};

export const put = async <B, T>({
  url,
  body,
  config,
}: HttpParamater<B>): Promise<AxiosResponse<T>> => {
  return http().put(url, body, config);
};

export const remove = async <B, T>({
  url,
  config,
}: HttpParamater<B>): Promise<AxiosResponse<T>> => {
  return http().delete(url, config);
};
