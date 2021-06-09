#include frex:shaders/api/fragment.glsl
#include frex:shaders/lib/math.glsl

void frx_startFragment(inout frx_FragmentData fragData) {
	fragData.emissivity = frx_luminance(fragData.spriteColor.rgb) > 0.33 ? 0.5 : 0;
}
