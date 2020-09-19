#include frex:shaders/api/fragment.glsl
#include frex:shaders/lib/math.glsl

void frx_startFragment(inout frx_FragmentData fragData) {
	float light = frx_luminance(fragData.spriteColor.rgb) * 2 - 0.3;
	fragData.emissivity = clamp(light, 0, 1);
}
