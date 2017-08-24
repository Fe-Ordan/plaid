/*
 *  Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package io.plaidapp.util.glide

import android.support.rastermill.FrameSequence
import android.support.rastermill.FrameSequenceDrawable
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.ResourceDecoder
import java.io.InputStream

/**
 * Decodes [InputStream]s into [FrameSequenceDrawableResource]s.
 */
class InputStreamFrameSequenceGifDecoder(private val bitmapProvider: FrameSequenceDrawable.BitmapProvider
) : ResourceDecoder<InputStream, FrameSequenceDrawable> {

    override fun handles(source: InputStream, options: Options): Boolean {
        val header = ByteArray(3)
        if (source.read(header) == -1) return false
        return header[0] == G && header[1] == I && header[2] == F
    }

    override fun decode(source: InputStream, width: Int, height: Int, options: Options) =
        FrameSequenceDrawableResource(FrameSequence.decodeStream(source), bitmapProvider)

    companion object {
        private const val G = 'G'.toByte()
        private const val I = 'I'.toByte()
        private const val F = 'F'.toByte()
    }

}
