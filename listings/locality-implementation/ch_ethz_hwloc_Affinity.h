#include <jni.h>
#ifndef _Included_ch_ethz_hwloc_Affinity
#define _Included_ch_ethz_hwloc_Affinity
#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL Java_ch_ethz_hwloc_Affinity_set__I //*\label{lst:locality-implementation-core-affinity-jni-h-set-unit}
  (JNIEnv *, jclass, jint);

JNIEXPORT void JNICALL Java_ch_ethz_hwloc_Affinity_set___3I //*\label{lst:locality-implementation-core-affinity-jni-h-set-node}
  (JNIEnv *, jclass, jintArray);

#ifdef __cplusplus
}
#endif
#endif
