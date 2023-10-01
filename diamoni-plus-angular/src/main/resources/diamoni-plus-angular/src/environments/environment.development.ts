export const environment = {
  auth: {
    login: '/api/auth/login',
    register: '/api/auth/register',
    retrieveUserInfo: '/api/auth/retrieve_user_info'
  },
  profile: {
    common: '/api/profiles',
    getUserProfile: '/api/profiles/:username',
    image: '/api/profiles/:username/image'
  },
  rentalSpace: {
    retrieveRentalImage: '/api/rental-spaces/:rentalSpaceReference/images/:binaryIdentification',
    myRentalSpaces: '/api/rental-spaces/host',
    uploadRentalImage: '/api/rental-spaces/images',
    deleteRentalImage: '/api/rental-spaces/images/:binaryIdentification',
    common: '/api/rental-spaces',
    rentalSpaceDetails: '/api/rental-spaces/:rentalSpaceReference'
  },
  reviews: '/api/reviews',
  booking: {
    common: '/api/booking',
    delete: '/api/booking/:bookingReference'
  },
  admin: {
    host: '/api/admin/host/:username',
    users: '/api/admin/users',
    exportApplicationData: '/api/admin/export'
  },
  discussions: {
    create: '/api/discussions/',
    myDiscussions: '/api/discussions/users',
    messages: '/api/discussions/messages',
    deleteMessage: '/api/discussions/messages/:messageId'
  }
};
