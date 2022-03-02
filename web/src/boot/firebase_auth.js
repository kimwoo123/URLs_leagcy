import { boot } from 'quasar/wrappers'
import { initializeApp } from 'firebase/app'

export default boot(async (/* { app, router, ... } */) => {
  const firebaseConfig = {
    apiKey: process.env.VUE_APP_FIREBASE_API_KEY,
    authDomain: process.env.VUE_APP_FIREBASE_AUTH_DOMAIN,
    projectId: process.env.VUE_APP_FIREBASE_PROJECT_ID,
    storageBucket: process.env.VUE_APP_FIREBASE_STORAGE_BUCKET,
    messagingSenderId: process.env.VUE_APP_FIREBASE_MESSAGEING_SENDER_ID,
    appId: process.env.VUE_APP_FIREBASE_APP_ID,
    measurementId: process.env.VUE_APP_FIREBASE_MEASUREMENT_ID
  };
  // something to do
  initializeApp(firebaseConfig)
  require('firebaseui')
  require('firebase/app')
})
