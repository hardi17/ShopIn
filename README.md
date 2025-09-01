# 🛒 ShopIn App
This shopping app is designed to make online purchasing simple, fast and secure. Users can:

🔐 Sign up or log in with Firebase authentication

🛍️ Browse products by categories

❤️ Add items to a favourite list

🛒 Add or remove items from the cart

💳 Complete checkout using card payments or cash on delivery

🚪 Logout easily with smooth session handling

A clean navigation flow, intuitive design, and responsive UI ensure a great user experience.
From a technical standpoint, the app leverages the powerful combination of Kotlin, Jetpack libraries, Hilt and Firebase to deliver a scalable, maintainable, and modern Android shopping solution.

## Main Features 
- Kotlin → Concise, safe, and modern programming language for Android
- Jetpack Compose → Declarative UI framework for building clean and responsive screens
- ViewModel (MVVM Architecture) → State management and lifecycle awareness
- Hilt (Dependency Injection) → Simplifies dependency management, improves testability, and enforces clean architecture
- Firebase Authentication → Secure signup/login with session handling
- Firebase Firestore → Real-time NoSQL database for cart, favourites, and products
- Payment Gateway Integration → Supports card payments and cash on delivery
- Coil → Fast and lightweight image loading library for Android

## Screens
<p float="left">
  <img src="https://github.com/user-attachments/assets/af40558b-b21b-4cf2-b25c-d29681b773bc" width="20%" />
  <img width="20%" src="https://github.com/user-attachments/assets/4eb85943-9a6e-4208-a86c-59cfad80e560" />
  <img width="20%" src="https://github.com/user-attachments/assets/e6eee418-4cb9-4946-93d2-321bb6bb8da4" />
<img width="20%"  src="https://github.com/user-attachments/assets/55c93887-1c30-49a4-819c-11ad8490573b" />
<img width="20%" height="2400" alt="product detail" src="https://github.com/user-attachments/assets/00696081-71a2-4279-ae6b-a4836b541442" />
<img width="20%"  alt="cart" src="https://github.com/user-attachments/assets/898fae31-57c2-46ab-99c8-5654a5716de3" />
<img width="20%" alt="checkout" src="https://github.com/user-attachments/assets/44ac333c-3454-487e-be96-ff9f9b7518c9" />
<img width="20%" alt="Payment" src="https://github.com/user-attachments/assets/0270be01-9275-4fa9-a8bd-8258636e9e46" />
<img width="20%" alt="COD order" src="https://github.com/user-attachments/assets/02adafe7-3594-4537-925c-76f3dc2b61b5" />
<img width="20%" alt="card order" src="https://github.com/user-attachments/assets/b0556d61-5323-4429-8e5c-6556c93a235a" />
<img width="20%"  alt="empty cart" src="https://github.com/user-attachments/assets/de401f09-6f75-44b2-8ba8-30ffc3df7f8d" />
<img width="20%" alt="Profile " src="https://github.com/user-attachments/assets/10325c64-9b1d-43b3-b980-324754562438" />

</p>

## Dependencies 
````
Note: Replace the versions above with the latest stable versions when implementing.

// Hilt (Dependency Injection)
implementation "com.google.dagger:hilt-android:2.48"
kapt "com.google.dagger:hilt-android-compiler:2.48"

// Firebase
implementation platform("com.google.firebase:firebase-bom:32.3.1")
implementation "com.google.firebase:firebase-auth-ktx"
implementation "com.google.firebase:firebase-firestore-ktx"

// Razorpay Payment Gateway
implementation "com.razorpay:checkout:1.6.33"

// Coil (Image Loading)
implementation "io.coil-kt:coil-compose:2.4.0"
````
## Hi there! 👋
I’m Hardi Rachh, an experienced Android developer who loves exploring new frameworks and tools.
Always open to connect and collaborate, feel free to reach out to me on: 😊

- LinkedIn : https://www.linkedin.com/in/hardi-r/
- Medium : https://hardirachh.medium.com/
