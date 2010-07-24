#include <jni.h>
#ifndef _Included_ch_ethz_hwloc_Affinity
#define _Included_ch_ethz_hwloc_Affinity
#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL Java_ch_ethz_hwloc_Affinity_set__I //*\label{lst:core-affinity-jni-h-set-unit}
  (JNIEnv *, jclass, jint);

JNIEXPORT void JNICALL Java_ch_ethz_hwloc_Affinity_set___3I //*\label{lst:core-affinity-jni-h-set-node}
  (JNIEnv *, jclass, jintArray);

JNIEXPORT jbooleanArray JNICALL //*\label{lst:core-affinity-jni-h-get}
  Java_ch_ethz_hwloc_Affinity_get (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif
