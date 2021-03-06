package com.thejuki.kformmaster.model

import android.app.AlertDialog
import android.widget.ImageView
import com.github.dhaval2404.imagepicker.constant.ImageProvider
import com.squareup.picasso.Transformation
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.CircleTransform
import com.thejuki.kformmaster.helper.ImagePickerOptions
import java.io.File

/**
 * Form Image Element
 *
 * Form element for Header TextView
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */
class FormImageElement(tag: Int = -1) : BaseFormElement<String>(tag) {

    /**
     * Unit called when an image is selected. A File object is returned.
     */
    var onSelectImage: ((image: File?) -> Unit)? = null

    /**
     * Unit called when the initial image is loaded from value or defaultImage.
     */
    var onInitialImageLoaded: (() -> Unit)? = null

    /**
     * Unit called when an image selection is cancelled.
     */
    var onCancel: (() -> Unit)? = null

    /**
     * Unit called when the image is cleared.
     */
    var onClear: (() -> Unit)? = null

    /**
     * The default image to display when the initial value is null or invalid.
     */
    var defaultImage: Int? = null

    /**
     * The Picasso Image Transformation.
     * By default this is [CircleTransform].
     */
    var imageTransformation: Transformation? = CircleTransform()

    /**
     * Default Image Picker Options
     */
    internal var defaultPickerOptions = ImagePickerOptions()

    /**
     * Default Image Picker Handler
     */
    internal var imagePickerOptionsFor: ((ImagePickerOptions) -> ImagePickerOptions) = {
        if (imagePickerOptions != null)
            imagePickerOptions?.invoke(it)

        defaultPickerOptions
    }

    /**
     * Image Picker Options
     */
    var imagePickerOptions: ((ImagePickerOptions) -> Unit)? = null

    /**
     * On-click listener unit for when the form element is clicked.
     * Set this Unit to override the default alert dialog that is displayed instead.
     */
    var onClickListener: (() -> Unit)? = null

    /**
     * Internal ImageProvider unit
     */
    internal var mOpenImagePicker: ((ImageProvider) -> Unit)? = null

    /**
     * Invokes the internal ImageProvider unit
     */
    fun openImagePicker(provider: ImageProvider) {
        mOpenImagePicker?.invoke(provider)
    }

    /**
     * Internal clear image unit
     */
    internal var mClearImage: (() -> Unit)? = null

    /**
     * Invokes the internal clear image unit
     */
    fun clearImage() {
        mClearImage?.invoke()
    }

    /**
     * Theme
     */
    var theme: Int = 0

    /**
     * Alert Dialog Builder
     * Used to call reInitDialog without needing context again.
     */
    private var alertDialogBuilder: AlertDialog.Builder? = null

    /**
     * Re-initializes the dialog
     */
    fun initDialog() {
        val imageView = this.editView as ImageView

        // reformat the options in format needed
        val options = arrayOf(imageView.context.getString(R.string.form_master_picker_camera), imageView.context.getString(R.string.form_master_picker_gallery), imageView.context.getString(R.string.form_master_picker_remove), imageView.context.getString(R.string.form_master_cancel))

        lateinit var alertDialog: AlertDialog

        if (alertDialogBuilder == null && imageView.context != null) {
            alertDialogBuilder = AlertDialog.Builder(imageView.context, theme)
        }

        alertDialogBuilder?.let {
            it.setTitle(imageView.context.getString(R.string.form_master_pick_one)).setMessage(null)
                    .setPositiveButton(null, null)
                    .setNegativeButton(null, null)

            it.setItems(options) { _, which ->
                this.error = null
                when (which) {
                    0 -> {
                        openImagePicker(ImageProvider.CAMERA)
                    }
                    1 -> {
                        openImagePicker(ImageProvider.GALLERY)
                    }
                    2 -> {
                        clearImage()
                    }
                }
            }

            alertDialog = it.create()
        }

        itemView?.setOnClickListener {
            if (onClickListener == null) {
                alertDialog.show()
            } else {
                onClickListener?.invoke()
            }
        }
    }
}