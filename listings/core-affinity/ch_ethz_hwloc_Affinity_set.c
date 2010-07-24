#define _GNU_SOURCE
#include <syscall.h>
#include <sched.h>
#include <pthread.h>
#include <stdbool.h>
#include <unistd.h>
#include "ch_ethz_hwloc_Affinity.h"

void set_affinity(JNIEnv *env, const cpu_set_t *cpuset);

JNIEXPORT void JNICALL
Java_ch_ethz_hwloc_Affinity_set__I(JNIEnv *env, 
		jclass class, jint physical_unit) {
	cpu_set_t cpuset;
	CPU_ZERO(&cpuset); //*\label{lst:core-affinity-jni-set-unit-init-cpuset}
	CPU_SET(physical_unit, &cpuset); //*\label{lst:core-affinity-jni-set-unit-set-cpuset}
	set_affinity(env, &cpuset);
}

JNIEXPORT void JNICALL
Java_ch_ethz_hwloc_Affinity_set___3I(JNIEnv *env, 
		jclass class, jintArray physical_units) {
	int i;
	cpu_set_t cpuset;
	CPU_ZERO(&cpuset); //*\label{lst:core-affinity-jni-set-node-init-cpuset}

	jsize len = (*env)->GetArrayLength(env, physical_units);
	jint *units = (*env)->GetIntArrayElements(env, 
			physical_units, 0);
	for (i = 0; i < len; i++)
		CPU_SET(units[i], &cpuset); //*\label{lst:core-affinity-jni-set-node-set-cpuset}

	set_affinity(env, &cpuset);
}

void set_affinity(JNIEnv *env, const cpu_set_t *cpuset) {  //*\label{lst:core-affinity-jni-set-impl-start}
	int s;
	pthread_t thread = pthread_self(); //*\label{lst:core-affinity-jni-set-impl-pthread-self}

	s = pthread_setaffinity_np(thread, sizeof(cpu_set_t), //*\label{lst:core-affinity-jni-set-impl-set}
			cpuset);
	if (s != 0) //*\label{lst:core-affinity-jni-set-impl-set-error}
		(*env)->ThrowNew(env, (*env)->FindClass(env,
				"ch/ethz/hwloc/SetAffinityException"), 
				"Couldn't set affinity!");
}  //*\label{lst:core-affinity-jni-set-impl-end}
