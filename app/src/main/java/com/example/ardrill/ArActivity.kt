package com.example.ardrill

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.ar.core.*
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Color
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ShapeFactory
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class ARActivity : AppCompatActivity() {

    private lateinit var arFragment: ArFragment
    private var objectPlaced = false
    private val CAMERA_PERMISSION_CODE = 100
    private var drillName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aractivity)

        drillName = intent.getStringExtra("drill_name") ?: ""
        Log.d("ARActivity", "Received drill name: $drillName")

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE
            )
        } else {
            setupAR()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            setupAR()
        } else {
            Toast.makeText(this, "Camera permission is required", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun setupAR() {
        val availability = ArCoreApk.getInstance().checkAvailability(this)
        if (!availability.isSupported) {
            Toast.makeText(this, "AR is not supported on this device", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        arFragment = supportFragmentManager.findFragmentById(R.id.ar_fragment) as ArFragment

        arFragment.setOnTapArPlaneListener { hitResult: HitResult, _: Plane, _ ->
            if (!objectPlaced) {
                when (drillName) {

                    "Drill 1" -> {
                        val color = Color(android.graphics.Color.RED)
                        MaterialFactory.makeOpaqueWithColor(this, color)
                            .thenAccept { material ->
                                val shape = ShapeFactory.makeCube(
                                    Vector3(0.1f, 0.1f, 0.1f),
                                    Vector3.zero(),
                                    material
                                )
                                placeObject(shape, hitResult)
                            }
                    }

                    "Drill 2" -> {
                        val color = Color(android.graphics.Color.GREEN)
                        MaterialFactory.makeOpaqueWithColor(this, color)
                            .thenAccept { material ->
                                val shape = ShapeFactory.makeCube(
                                    Vector3(0.2f, 0.05f, 0.1f), // rectangle
                                    Vector3.zero(),
                                    material
                                )
                                placeObject(shape, hitResult)
                            }
                    }

                    "Drill 3" -> {
                        val color = Color(android.graphics.Color.BLUE)
                        MaterialFactory.makeOpaqueWithColor(this, color)
                            .thenAccept { material ->
                                val shape = ShapeFactory.makeSphere(
                                    0.07f, // sphere
                                    Vector3.zero(),
                                    material
                                )
                                placeObject(shape, hitResult)
                            }
                    }

                    else -> {
                        val color = Color(android.graphics.Color.GRAY)
                        MaterialFactory.makeOpaqueWithColor(this, color)
                            .thenAccept { material ->
                                val shape = ShapeFactory.makeCube(
                                    Vector3(0.1f, 0.1f, 0.1f),
                                    Vector3.zero(),
                                    material
                                )
                                placeObject(shape, hitResult)
                            }
                    }
                }
            }
        }
    }

    private fun placeObject(shapeRenderable: com.google.ar.sceneform.rendering.ModelRenderable, hitResult: HitResult) {
        shapeRenderable.isShadowCaster = false
        shapeRenderable.isShadowReceiver = false

        val anchor = hitResult.createAnchor()
        val anchorNode = AnchorNode(anchor)
        anchorNode.setParent(arFragment.arSceneView.scene)

        val node = TransformableNode(arFragment.transformationSystem)
        node.renderable = shapeRenderable
        node.setParent(anchorNode)
        node.select()

        objectPlaced = true
        arFragment.arSceneView.planeRenderer.isVisible = false
        arFragment.arSceneView.planeRenderer.isEnabled = false

    }
}
