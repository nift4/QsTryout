/*
 * Copyright (C) 2017 The Android Open Source Project
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

package org.eu.droid_ng.systemui.qs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.android.systemui.plugins.qs.QSTile.SlashState;

public class SlashImageView extends ImageView {

    @VisibleForTesting
    protected SlashDrawable mSlash;
    private boolean mAnimationEnabled = true;

    public SlashImageView(Context context) {
        super(context);
    }

    protected SlashDrawable getSlash() {
        return mSlash;
    }

    protected void setSlash(SlashDrawable slash) {
        mSlash = slash;
    }

    protected void ensureSlashDrawable() {
        if (mSlash == null) {
            mSlash = new SlashDrawable(getDrawable());
            mSlash.setAnimationEnabled(mAnimationEnabled);
            super.setImageDrawable(mSlash);
        }
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        if (drawable == null) {
            mSlash = null;
            super.setImageDrawable(null);
        } else if (mSlash == null) {
            setImageLevel(drawable.getLevel());
            super.setImageDrawable(drawable);
        } else {
            mSlash.setAnimationEnabled(mAnimationEnabled);
            mSlash.setDrawable(drawable);
        }
    }

    protected void setImageViewDrawable(SlashDrawable slash) {
        super.setImageDrawable(slash);
    }

    public void setAnimationEnabled(boolean enabled) {
        mAnimationEnabled = enabled;
    }

    public boolean getAnimationEnabled() {
        return mAnimationEnabled;
    }

    private void setSlashState(@NonNull SlashState slashState) {
        ensureSlashDrawable();
        mSlash.setRotation(slashState.rotation);
        mSlash.setSlashed(slashState.isSlashed);
    }

    public void setState(SlashState state, Drawable drawable) {
        if (state != null) {
            setImageDrawable(drawable);
            setSlashState(state);
        } else {
            mSlash = null;
            setImageDrawable(drawable);
        }
    }
}
