package com.example.myapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun onNameChange(newName: String) {
        _uiState.update { it.copy(name = newName) }
    }

    fun onEmailChange(newEmail: String) {
        _uiState.update { it.copy(email = newEmail) }
    }

    fun onContactChange(newContact: String) {
        _uiState.update { it.copy(contactNumber = newContact) }
    }

    fun onAddressChange(newAddress: String) {
        _uiState.update { it.copy(address = newAddress) }
    }

    fun onUsernameChange(newUsername: String) {
        _uiState.update { it.copy(username = newUsername) }
    }

    fun onNewSkillChange(skillText: String) {
        _uiState.update { it.copy(newSkill = skillText) }
    }

    fun addSkill() {
        val skill = _uiState.value.newSkill.trim()
        if (skill.isNotEmpty()) {
            _uiState.update { 
                it.copy(
                    skills = it.skills + skill,
                    newSkill = ""
                )
            }
        }
    }

    fun removeSkill(skill: String) {
        _uiState.update { 
            it.copy(skills = it.skills.filter { s -> s != skill })
        }
    }

    fun showPreview() {
        _uiState.update { it.copy(isPreview = true) }
    }

    fun backToEdit() {
        _uiState.update { it.copy(isPreview = false) }
    }
}
