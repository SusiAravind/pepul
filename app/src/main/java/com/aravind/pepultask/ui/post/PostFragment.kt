package com.aravind.pepultask.ui.home

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.Context.NOTIFICATION_SERVICE
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.aravind.pepultask.R
import com.aravind.pepultask.data.model.Post
import com.aravind.pepultask.databinding.FragmentPostBinding
import com.aravind.pepultask.di.component.FragmentComponent
import com.aravind.pepultask.ui.base.BaseFragment
import com.aravind.pepultask.ui.main.MainSharedViewModel
import com.aravind.pepultask.utils.common.Constants
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import javax.inject.Inject


class PostFragment : BaseFragment<PostViewModel>() {
    private lateinit var exoplayer: SimpleExoPlayer
    private lateinit var playerNotificationManager: PlayerNotificationManager

    companion object {
        const val TAG = "HomeFragment"
    }

    // creating a variable for exoplayer
    var exoPlayer: ExoPlayer? = null

    @Inject
    lateinit var mainSharedViewModel: MainSharedViewModel

    private var _binding: FragmentPostBinding? = null

    private val binding get() = _binding!!

    override fun provideLayoutId(): Int = R.layout.fragment_post


    override fun injectDependencies(fragmentComponent: FragmentComponent) =
        fragmentComponent.inject(this)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setupView(view: View) {

        _binding = FragmentPostBinding.bind(view)
        initializePlayer()

    }

    override fun setupObservers() {
        super.setupObservers()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initializePlayer() {
        // Initialize ExoPlayer
        exoplayer = context?.let {
            SimpleExoPlayer.Builder(it)
                .build()
        }!!

        // Initialize PlayerNotificationManager
        initPlayerNotificationManager()

        // Set the exoPlayer to the playerView
        binding.idExoPlayerVIew.player = exoplayer

        // Create a MediaItem
        val mediaItem = createMediaItem()

        exoplayer.addMediaItem(mediaItem)
        exoplayer.prepare()
        exoplayer.play()
    }

    private fun createMediaItem(): MediaItem {
        val mediaUri = Uri.parse("asset:///heart_attack.mp3")
        return MediaItem.fromUri(mediaUri)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun initPlayerNotificationManager() {
        val channel = NotificationChannel(
            Constants.NOTIFICATION_CHANNEL,
            Constants.NOTIFICATION_CHANNEL,
            IMPORTANCE_HIGH
        )
        val notificationManager =
            context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        playerNotificationManager = PlayerNotificationManager.Builder(
            requireContext(),
            Constants.NOTIFICATION_ID,
            Constants.NOTIFICATION_CHANNEL,
            object : PlayerNotificationManager.MediaDescriptionAdapter {
                override fun getCurrentContentTitle(player: Player): CharSequence =
                    player.currentMediaItem?.mediaMetadata?.title ?: "Music"

                override fun createCurrentContentIntent(player: Player): PendingIntent? {
                    return null
                }

                override fun getCurrentContentText(player: Player): CharSequence? {
                    return "Music Content Text"
                }

                override fun getCurrentLargeIcon(
                    player: Player,
                    callback: PlayerNotificationManager.BitmapCallback
                ): Bitmap? {
                    return BitmapFactory.decodeResource(
                        context?.resources,
                        R.drawable.ic_baseline_play_circle_outline_24
                    )
                }

            })
            .build()
        playerNotificationManager.setPlayer(exoplayer)
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    private fun releasePlayer() {
        exoplayer.stop()
        playerNotificationManager.setPlayer(null)
        exoplayer.release()
    }


    private fun showDialog(post: Post) {
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog


        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(requireContext())

        // Set a title for alert dialog
        builder.setTitle("Delete Post")

        // Set a message for alert dialog
        builder.setMessage("Do you want to delete this post?")


        // On click listener for dialog buttons
        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                // DialogInterface.BUTTON_POSITIVE -> viewModel.deleteUserPost(post)
                DialogInterface.BUTTON_NEGATIVE -> dialog.dismiss()
            }
        }


        // Set the alert dialog positive/yes button
        builder.setPositiveButton("YES", dialogClickListener)

        // Set the alert dialog negative/no button
        builder.setNegativeButton("NO", dialogClickListener)

        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
