package com.github.bukunmiola.vdetekt.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.github.bukunmiola.vdetekt.MyBundle

@Service(Service.Level.PROJECT)
class MyProjectService(project: Project) {
    fun getRandomNumber() = (1..100).random()
}
