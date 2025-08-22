# ArDrill2
**AR Drill App** It is an Augmented Reality (AR) based Android application that provides users with an interactive experience to place 3D drills (cube, rectangle, circle)
#Features
📱 User-friendly UI using Jetpack Compose and Material 3 (v1.12.0)
- 🔽 Dropdown to choose from different drills
- 🔁 Previous/Next buttons to navigate drills
- 🧠 Each drill contains:
  - Description
  - Helpful tips
- 🚀 Launch AR experience using Sceneform
- 🎨 Modern design with smooth animations
#Projet Structure
com.example.ardrill/
│
├── ui.theme/
│   ├── AR.kt
│   ├── ArActivity.kt
│   ├── Drill.kt
│   ├── DrillScreen.kt
│   └── MainActivity.kt
│
├── activity_aractivity.xml
#Technology Stack
Kotlin- Programming Lanuage
Jetapck Compose- For ui Design
Sceneform - For Ar rendering
Materail 3 - UI Component(version =1.12.0)
Android SDK
Target version = 35

# How to use it
1. Clone or unzip the project folder
2. Open in Android Studio
3. Ensure you have:
   - ARCore-supported device
   - SDK version 35
   - Internet permission (if needed)
4. Build & run the app on a physical device

#Screenshots
![WhatsApp Image 2025-08-23 at 01 51 52_3fc5483e](https://github.com/user-attachments/assets/566ac829-62cd-4610-8737-53f4f372e27a)



#Layouts
**activity_aractivity.xml**: Used in `ArActivity.kt` to handle the AR fragment and surface detection.

#Developer
Azharul Islam
ai820641@gmail.com
