package com.rsmbyk.course.mdp.common

import java.io.File

fun File.deleteFileOnExit (): File {
    deleteOnExit ()
    return this
}

fun File.createNewDirectory (): File {
    if (exists () && isFile)
        deleteRecursively ()
    if (!exists ())
        mkdirs ()
    return this
}
