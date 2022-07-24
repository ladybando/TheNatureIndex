package com.example.android.thenatureindex.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.android.thenatureindex.databinding.FragmentGalleryBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeler
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val args: GalleryFragmentArgs by navArgs()
    private val viewModel: GalleryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this)[GalleryViewModel::class.java]

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bitmapImage = args.bitmapImage
        //pass in argument set as Bitmap in nav to InputImage() method
        val image = InputImage.fromBitmap(bitmapImage, 0)
        val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
        var text = ""
        var confidence = 0f

        labeler.process(image)
            .addOnSuccessListener { labels ->
                for (label in labels){
                    text_gallery.text = label.text
                    confidence = label.confidence
                    Log.i("ImageFrag", "LOOP>     [$text]:$confidence")
                }
                text_gallery.text = "$text: $confidence"
            }
            .addOnFailureListener { e -> Log.d("ImageError", "Error with labeling: $e") }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}