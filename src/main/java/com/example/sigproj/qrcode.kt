package com.example.sigproj

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.SurfaceView
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.zxing.Result
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CompoundBarcodeView
import android.content.Intent

@Suppress("DEPRECATION")
class qrcode : ComponentActivity() {

    private lateinit var barcodeView: CompoundBarcodeView
    private lateinit var res: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qr_activity)

        barcodeView = findViewById(R.id.barcodeView)

        if (checkCameraPermission()) {
            setupBarcodeScanner()
        } else {
            requestCameraPermission()
        }
    }

    private fun setupBarcodeScanner() {
        barcodeView.decodeSingle(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                result?.let {
                    // Handle the scanned QR code result
                    val scannedText = it.text
                    //res.text = scannedText
                    //Log.d("MainActivity","Scanned text is: $scannedText")
                    openurl(scannedText)
                    // Resume scanning after a short delay
                    Handler(Looper.getMainLooper()).postDelayed({
                        barcodeView.resume()
                    }, 2000)
                }
            }

            private fun openurl(url: String) {

                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = android.net.Uri.parse(url)
                startActivity(intent)
            }

            override fun possibleResultPoints(resultPoints: List<ResultPoint>?) {
                // Handle possible result points
            }
        })
    }

    private fun checkCameraPermission(): Boolean {
        return checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        requestPermissions(arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupBarcodeScanner()
            } else {
                // Handle permission denied
            }
        }
    }

    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }
}


