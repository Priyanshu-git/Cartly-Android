
package com.nexxlabs.cartly.main.dashboard.ui

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nexxlabs.cartly.R
import com.nexxlabs.cartly.databinding.DialogCreateGroupBinding

class NewGroupBottomSheet(
    private val onCreateGroup: (groupName: String, members: List<String>) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: DialogCreateGroupBinding? = null
    private val binding get() = _binding!!

    private val memberEmails = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCreateGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEmailChipHandling()
        setupButtons()
    }

    override fun onStart() {
        super.onStart()
        resetState()
    }

    private fun resetState() {
        binding.groupNameEditText.text?.clear()
        binding.memberEmailEditText.text?.clear()
        binding.emailChipGroup.removeAllViews()
    }

    private fun setupEmailChipHandling() {
        binding.memberEmailEditText.setOnEditorActionListener { _, _, _ ->
            addEmailFromInput()
            true
        }

        binding.memberEmailEditText.setOnKeyListener { _, keyCode, event ->
            if (event.action == android.view.KeyEvent.ACTION_DOWN &&
                (keyCode == android.view.KeyEvent.KEYCODE_ENTER ||
                        keyCode == android.view.KeyEvent.KEYCODE_SPACE ||
                        keyCode == android.view.KeyEvent.KEYCODE_COMMA)
            ) {
                addEmailFromInput()
                return@setOnKeyListener true
            }
            false
        }
    }

    private fun addEmailFromInput() {
        val email = binding.memberEmailEditText.text?.toString()?.trim()?.removeSuffix(",") ?: ""
        if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (!memberEmails.contains(email)) {
                memberEmails.add(email)
                addChip(email)
            } else {
                Toast.makeText(requireContext(), R.string.duplicate_email, Toast.LENGTH_SHORT).show()
            }
        } else if (email.isNotEmpty()) {
            Toast.makeText(requireContext(), R.string.invalid_email, Toast.LENGTH_SHORT).show()
        }
        binding.memberEmailEditText.text?.clear()
    }

    private fun addChip(email: String) {
        val chip = MemberChipView(requireContext()).apply {
            setEmail(email)
            setChipColors(
                bg = R.color.chip_background,
                textColor = R.color.chip_text,
                closeTint = R.color.chip_close_icon_tint
            )
            setOnCloseIconClickListener {
                binding.emailChipGroup.removeView(this)
                memberEmails.remove(email)
            }
        }
        binding.emailChipGroup.addView(chip)
    }

    private fun setupButtons() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnCreate.setOnClickListener {
            val groupName = binding.groupNameEditText.text?.toString()?.trim().orEmpty()
            if (groupName.isEmpty()) {
                Toast.makeText(requireContext(), R.string.group_name_required, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (memberEmails.isEmpty()) {
                Toast.makeText(requireContext(), R.string.no_members_added, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            onCreateGroup(groupName, memberEmails)
            dismiss()
        }
    }

    override fun getTheme(): Int = R.style.CustomBottomSheetDialogTheme

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
