package com.pslpro.futuremusic.ui.componsnts

import android.Manifest.permission.*
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.*


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)

@Composable
fun PermissionGetDialog(

) {

    var showPermissionDialog by remember { mutableStateOf(false) }

    var permissions: MultiplePermissionsState? = null

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        //安卓13以上获取权限
        permissions = rememberMultiplePermissionsState( listOf(
            android.Manifest.permission.READ_MEDIA_IMAGES,
            android.Manifest.permission.READ_MEDIA_AUDIO,
            android.Manifest.permission.READ_MEDIA_VIDEO))
        if (!permissions.allPermissionsGranted){
            showPermissionDialog = true
        }
    } else {
        //安卓13以下获取权限
        permissions = rememberMultiplePermissionsState(listOf(android.Manifest.permission.READ_EXTERNAL_STORAGE))
        if (!permissions.allPermissionsGranted){
            showPermissionDialog = true
        }
    }

    if (!permissions.allPermissionsGranted){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            //安卓13以上弹窗
            AlertDialog(
                title = { Text(text = "授权提示")},
                text = { Column() {
                    Text(text = "使用FutureMusic需要获取以下权限：")
                    Text(text = "图片和视频访问权限")
                    Text(text = "音乐访问权限")
                } },
                confirmButton = {
                    TextButton(
                        onClick = {
                            permissions.launchMultiplePermissionRequest()
                            showPermissionDialog = false
                        }
                    ) {
                        Text(text = "授权")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showPermissionDialog = false }) {
                        Text(text = "仅使用网络音乐", color = Color.Red)
                    }
                },
                onDismissRequest = {  },
            )
        }else{
            AlertDialog(
                //安卓13以下弹窗
                title = { Text(text = "授权提示")},
                text = { Text(text = "使用FutureMusic需要获取媒体文件访问权限")},
                confirmButton = {
                    TextButton(
                        onClick = {
                            showPermissionDialog = false
                            permissions.launchMultiplePermissionRequest()
                        }
                    ) {
                        Text(text = "授权")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showPermissionDialog = false }) {
                        Text(text = "仅使用网络音乐", color = Color.Red)
                    }
                },
                onDismissRequest = {  },
            )
        }
    }

}