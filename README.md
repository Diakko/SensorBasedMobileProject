# AN ANDROID APP
## FOR GETTING INFO FROM FOOD PRODUCTS BY SCANNING THE BAR CODE WITH YOUR PHONE
A school project for the course "Sensor Based Mobile Applications" (course info here: 
https://opinto-opas.metropolia.fi/en/realization/TX00CK66-3009)

### What the project does
The idea behind this app is to be able to scan a food product at your local grocery store and see if the food contains substances that may be cause an allergic reaction to you. It also gives an general overview of the products nutriments.

### Why the project is useful
You can check if the product contains any ingredients that aren't good for your health or the product contains allergens that you wont enjoy.

### How users can get started with the project
Download, open with Android Studio and start in the emulator

### Where users can get help with your project
You can contact the developers at `ville.pystynen@metropolia.fi` and `matias.hatonen@metropolia.fi

### Who maintains and contributes to the project
Ville Pystynen and Matias Hätönen


### INGREDIENTS

This app makes use of the following sensors:
- Step counter
- Camera
- GPS

It uses the following Android system's basic components:
- Room Database for persistence
- [JetPack Navigation](https://developer.android.com/guide/navigation)
- RecyclerView
- Coroutines
- ViewModel
- Fragments
- LiveData
- Activity
- Service
- Intents
- Views

The app is localized in Finnish, English (US) and in Swedish.
Accessibility was also considered in the design and development process. The also app has a light and dark theme.

It gets data from these web services:
- [Open Food Facts API](https://world.openfoodfacts.org/)
- [Nominatim API](https://nominatim.openstreetmaps.org/)

This app uses the following third party librabries:
- [ZXing ("zebra crossing")](https://github.com/zxing/zxing)
- [Retrofit](https://square.github.io/retrofit/)
- [osmdroid](https://github.com/osmdroid/osmdroid)

The OpenFoodFacts API response data classes were made with http://www.json2kotlin.com

### License
[GNU GPL2](https://www.gnu.org/licenses/old-licenses/gpl-2.0.html)