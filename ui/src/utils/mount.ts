export {};

// import MountService from '@/service/MountService';

// import type { MountCategory } from '@/types/collections/mounts';

// export async function wowheadMountInfo(mountCategories: MountCategory[]) {
//   const detailedMountInformation = await Promise.all(
//     mountCategories.map(async (category) =>
//       Promise.all(
//         category.mounts.map(async (mount) => {
//           const detailedMountInformation = await MountService.getMountInformation(mount.id);
//           return detailedMountInformation;
//         }),
//       ),
//     ),
//   );

//   // merge detailed mount information with mount categories
//   mountCategories.forEach((category, index) => {
//     category.mounts.forEach((mount, mountIndex) => {
//       category.mounts[mountIndex] = {
//         ...mount,
//         ...detailedMountInformation[index][mountIndex],
//       };
//     });
//   });

//   return mountCategories;
// }
