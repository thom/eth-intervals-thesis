JNIEXPORT jbooleanArray JNICALL
Java_ch_ethz_hwloc_Affinity_get(JNIEnv *env, jclass class) {
	int s, i, online_cpus;
	cpu_set_t cpuset;
	pthread_t thread;
	jintArray result;

	// Number of online processors - same as
	// Runtime.getRuntime().availableProcessors()
	online_cpus = sysconf(_SC_NPROCESSORS_ONLN);

	// Create Java array
	result = (*env)->NewIntArray(env, online_cpus);
	if (result == NULL) {
		(*env)->ThrowNew(env, (*env)->FindClass(env,
				"java/lang/OutOfMemoryError"), "Out of memory!");
	}

	thread = pthread_self(); //*\label{lst:locality-implementation-core-affinity-jni-get-impl-pthread-self}

	s = pthread_getaffinity_np(thread, sizeof(cpu_set_t), //*\label{lst:locality-implementation-core-affinity-jni-get-impl-get}
			&cpuset);
	if (s != 0) {
		(*env)->ThrowNew(env, (*env)->FindClass(env,
				"ch/ethz/hwloc/GetAffinityException"), 
				"Couldn't get affinity!");
	}

	jboolean tmp[online_cpus];
	for (i = 0; i < online_cpus; i++) {
		if (CPU_ISSET(i, &cpuset)) {
			tmp[i] = true;
		}
		else {
			tmp[i] = false;
		}
	}

	// Move from the temporary array to the Java array
	(*env)->SetBooleanArrayRegion(env, result, 0, 
			online_cpus, tmp);

	return result;
}
