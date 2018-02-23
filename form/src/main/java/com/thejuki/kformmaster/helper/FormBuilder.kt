package com.thejuki.kformmaster.helper

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.widget.ArrayAdapter
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener
import com.thejuki.kformmaster.model.*
import java.io.Serializable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Form Builder
 *
 * Used for Kotlin DSL to create the FormBuildHelper
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */

/** Type-safe builder method to initialize the form */
fun form(context: Context,
         recyclerView: RecyclerView,
         listener: OnFormElementValueChangedListener? = null,
         init: FormBuildHelper.() -> Unit): FormBuildHelper {
    val form = FormBuildHelper(
            context = context,
            listener = listener,
            recyclerView = recyclerView
    )
    form.init()
    form.setItems()
    form.refreshView()
    return form
}

interface FieldBuilder {
    fun build(): BaseFormElement<*>
}

/** Builder method to add a header */
class HeaderBuilder() : FieldBuilder {
    var title: String = ""
    override fun build() =
            FormHeader.createInstance(title)
}
fun FormBuildHelper.header(init: HeaderBuilder.() -> Unit): FormHeader {
    val element = HeaderBuilder().apply(init).build()
    element.id = ++lastId
    elements.add(element)
    return element
}

/** Builder method to add a BaseFormElement */
abstract class BaseElementBuilder<T: Serializable>(protected val tag: Int) : FieldBuilder {
    var title: String? = null // title for the form element
    var type: Int = 0 // type for the form element
    var value: T? = null // value to be shown on right
    var options: List<T>? = null // list of options for single and multi picker
        get() = field ?: ArrayList()
    var optionsSelected: List<T>? = null // list of selected options for single and multi picker
        get() = field ?: ArrayList()
    var hint: String? = null // value to be shown if value is null
    var error: String? = null
    var required: Boolean = false // value to set is the field is required
    var visible: Boolean = true
    var valueChanged: OnFormElementValueChangedListener? = null
}

/** Builder method to add a FormSingleLineEditTextElement */
class SingleLineEditTextBuilder(tag: Int) : BaseElementBuilder<String>(tag) {
    override fun build() =
            FormSingleLineEditTextElement(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormSingleLineEditTextElement
}

fun FormBuildHelper.text(tag: Int, init: SingleLineEditTextBuilder.() -> Unit): FormSingleLineEditTextElement {
    val element = SingleLineEditTextBuilder(tag).apply(init).build()
    element.id = ++lastId
    elements.add(element)
    return element
}

/** Builder method to add a FormMultiLineEditTextElement */
class MultiLineEditTextBuilder(tag: Int) : BaseElementBuilder<String>(tag) {
    override fun build() =
            FormMultiLineEditTextElement(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormMultiLineEditTextElement
}

fun FormBuildHelper.textArea(tag: Int, init: MultiLineEditTextBuilder.() -> Unit): FormMultiLineEditTextElement {
    val element = MultiLineEditTextBuilder(tag).apply(init).build()
    element.id = ++lastId
    elements.add(element)
    return element
}

/** Builder method to add a FormNumberEditTextElement */
class NumberEditTextBuilder(tag: Int) : BaseElementBuilder<String>(tag) {
    override fun build() =
            FormNumberEditTextElement(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormNumberEditTextElement
}

fun FormBuildHelper.number(tag: Int, init: NumberEditTextBuilder.() -> Unit): FormNumberEditTextElement {
    val element = NumberEditTextBuilder(tag).apply(init).build()
    element.id = ++lastId
    elements.add(element)
    return element
}

/** Builder method to add a FormEmailEditTextElement */
class EmailEditTextBuilder(tag: Int) : BaseElementBuilder<String>(tag) {
    override fun build() =
            FormEmailEditTextElement(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormEmailEditTextElement
}

fun FormBuildHelper.email(tag: Int, init: EmailEditTextBuilder.() -> Unit): FormEmailEditTextElement {
    val element = EmailEditTextBuilder(tag).apply(init).build()
    element.id = ++lastId
    elements.add(element)
    return element
}

/** Builder method to add a FormPhoneEditTextElement */
class PasswordEditTextBuilder(tag: Int) : BaseElementBuilder<String>(tag) {
    override fun build() =
            FormPasswordEditTextElement(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormPasswordEditTextElement
}

fun FormBuildHelper.password(tag: Int, init: PasswordEditTextBuilder.() -> Unit): FormPasswordEditTextElement {
    val element = PasswordEditTextBuilder(tag).apply(init).build()
    element.id = ++lastId
    elements.add(element)
    return element
}

/** Builder method to add a FormEmailEditTextElement */
class PhoneEditTextBuilder(tag: Int) : BaseElementBuilder<String>(tag) {
    override fun build() =
            FormPhoneEditTextElement(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormPhoneEditTextElement
}

fun FormBuildHelper.phone(tag: Int, init: PhoneEditTextBuilder.() -> Unit): FormPhoneEditTextElement {
    val element = PhoneEditTextBuilder(tag).apply(init).build()
    element.id = ++lastId
    elements.add(element)
    return element
}

/** Builder method to add a FormAutoCompleteElement */
class AutoCompleteBuilder<T: Serializable>(tag: Int) : BaseElementBuilder<T>(tag) {
    var arrayAdapter: ArrayAdapter<*>? = null
    var dropdownWidth: Int? = null
    override fun build() =
            (FormAutoCompleteElement<T>(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormAutoCompleteElement<T>)
                    .setArrayAdapter(arrayAdapter)
                    .setDropdownWidth(dropdownWidth)
}
fun <T: Serializable> FormBuildHelper.autoComplete(tag: Int, init: AutoCompleteBuilder<T>.() -> Unit): FormAutoCompleteElement<T> {
    val element = AutoCompleteBuilder<T>(tag).apply(init).build()
    element.id = ++lastId
    elements.add(element)
    return element
}

/** Builder method to add a FormTokenAutoCompleteElement */
class AutoCompleteTokenBuilder<T: Serializable>(tag: Int) : BaseElementBuilder<T>(tag) {
    var arrayAdapter: ArrayAdapter<*>? = null
    var dropdownWidth: Int? = null
    override fun build() =
            (FormTokenAutoCompleteElement<T>(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormTokenAutoCompleteElement<T>)
                    .setArrayAdapter(arrayAdapter)
                    .setDropdownWidth(dropdownWidth)
}
fun <T: Serializable> FormBuildHelper.autoCompleteToken(tag: Int, init: AutoCompleteTokenBuilder<T>.() -> Unit): FormTokenAutoCompleteElement<T> {
    val element = AutoCompleteTokenBuilder<T>(tag).apply(init).build()
    element.id = ++lastId
    elements.add(element)
    return element
}

/** Builder method to add a FormButtonElement */
class ButtonBuilder(val tag: Int) : FieldBuilder {
    var value: String? = null
    var visible: Boolean = true
    var valueChanged: OnFormElementValueChangedListener? = null
    override fun build() =
            (FormButtonElement(tag)
                    .setValue(value)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormButtonElement)
}

fun FormBuildHelper.button(tag: Int, init: ButtonBuilder.() -> Unit): FormButtonElement {
    val element = ButtonBuilder(tag).apply(init).build()
    element.id = ++lastId
    elements.add(element)
    return element
}

/** Builder method to add a FormPickerDateElement */
class DateBuilder(tag: Int) : BaseElementBuilder<FormPickerDateElement.DateHolder>(tag) {
    var dateFormat: DateFormat = SimpleDateFormat.getDateInstance()
    var dateValue: Date? = null
    override fun build() =
            (FormPickerDateElement(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value?: FormPickerDateElement.DateHolder(dateValue, dateFormat))
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormPickerDateElement)
}
fun FormBuildHelper.date(tag: Int, init: DateBuilder.() -> Unit): FormPickerDateElement {
    val element = DateBuilder(tag).apply(init).build()
    element.id = ++lastId
    elements.add(element)
    return element
}

/** Builder method to add a FormPickerTimeElement */
class TimeBuilder(tag: Int) : BaseElementBuilder<FormPickerDateElement.DateHolder>(tag) {
    var dateFormat: DateFormat = SimpleDateFormat.getDateInstance()
    var dateValue: Date? = null
    override fun build() =
            (FormPickerTimeElement(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value?: FormPickerTimeElement.TimeHolder(dateValue, dateFormat))
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormPickerTimeElement)
}
fun FormBuildHelper.time(tag: Int, init: TimeBuilder.() -> Unit): FormPickerTimeElement {
    val element = TimeBuilder(tag).apply(init).build()
    element.id = ++lastId
    elements.add(element)
    return element
}

/** Builder method to add a FormButtonElement */
class DateTimeBuilder(tag: Int) : BaseElementBuilder<FormPickerDateElement.DateHolder>(tag) {
    var dateFormat: DateFormat = SimpleDateFormat.getDateInstance()
    var dateValue: Date? = null
    override fun build() =
            (FormPickerDateTimeElement(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value?: FormPickerDateTimeElement.DateTimeHolder(dateValue, dateFormat))
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormPickerDateTimeElement)
}
fun FormBuildHelper.dateTime(tag: Int, init: DateTimeBuilder.() -> Unit): FormPickerDateTimeElement {
    val element = DateTimeBuilder(tag).apply(init).build()
    element.id = ++lastId
    elements.add(element)
    return element
}

/** Builder method to add a FormPickerDropDownElement */
class DropDownBuilder<T: Serializable>(tag: Int) : BaseElementBuilder<T>(tag) {
    var dialogTitle: String? = null
    var arrayAdapter: ArrayAdapter<*>? = null
    override fun build() =
            (FormPickerDropDownElement<T>(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setOptions(options?: ArrayList())
                    .setValueChanged(valueChanged)
                    as FormPickerDropDownElement<T>)
                    .setDialogTitle(dialogTitle)
                    .setArrayAdapter(arrayAdapter)
}
fun <T: Serializable> FormBuildHelper.dropDown(tag: Int, init: DropDownBuilder<T>.() -> Unit): FormPickerDropDownElement<T> {
    val element = DropDownBuilder<T>(tag).apply(init).build()
    element.id = ++lastId
    elements.add(element)
    return element
}

/** Builder method to add a FormPickerMultiCheckBoxElement */
class MultiCheckBoxBuilder<T: Serializable>(tag: Int) : BaseElementBuilder<T>(tag) {
    var dialogTitle: String? = null
    override fun build() =
            (FormPickerMultiCheckBoxElement<T>(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setOptions(options?: ArrayList())
                    .setOptionsSelected(optionsSelected?: ArrayList())
                    .setValueChanged(valueChanged)
                    as FormPickerMultiCheckBoxElement<T>)
                    .setDialogTitle(dialogTitle)
}
fun <T: Serializable> FormBuildHelper.multiCheckBox(tag: Int, init: MultiCheckBoxBuilder<T>.() -> Unit): FormPickerMultiCheckBoxElement<T> {
    val element = MultiCheckBoxBuilder<T>(tag).apply(init).build()
    element.id = ++lastId
    elements.add(element)
    return element
}

/** Builder method to add a FormSwitchElement */
class SwitchBuilder<T: Serializable>(tag: Int) : BaseElementBuilder<T>(tag) {
    var onValue: T? = null
    var offValue: T? = null
    override fun build() =
            (FormSwitchElement<T>(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormSwitchElement<T>)
                    .setOnValue(onValue)
                    .setOffValue(offValue)
}
fun <T: Serializable> FormBuildHelper.switch(tag: Int, init: SwitchBuilder<T>.() -> Unit): FormSwitchElement<T> {
    val element = SwitchBuilder<T>(tag).apply(init).build()
    element.id = ++lastId
    elements.add(element)
    return element
}

/** Builder method to add a FormSwitchElement */
class CheckBoxBuilder<T : Serializable>(tag: Int) : BaseElementBuilder<T>(tag) {
    var checkedValue: T? = null
    var unCheckedValue: T? = null
    override fun build() =
            (FormCheckBoxElement<T>(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormCheckBoxElement<T>)
                    .setCheckedValue(checkedValue)
                    .setUnCheckedValue(unCheckedValue)
}

fun <T : Serializable> FormBuildHelper.checkBox(tag: Int, init: CheckBoxBuilder<T>.() -> Unit): FormCheckBoxElement<T> {
    val element = CheckBoxBuilder<T>(tag).apply(init).build()
    element.id = ++lastId
    elements.add(element)
    return element
}

/** Builder method to add a FormSwitchElement */
class SliderBuilder(tag: Int) : BaseElementBuilder<Int>(tag) {
    var max: Int = 100
    var min: Int = 0
    var steps: Int = 1
    override fun build() =
            (FormSliderElement(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setHint(hint)
                    .setError(error)
                    .setRequired(required)
                    .setVisible(visible)
                    .setValueChanged(valueChanged)
                    as FormSliderElement)
                    .setMax(max)
                    .setMin(min)
                    .setSteps(steps)
}
fun FormBuildHelper.slider(tag: Int, init: SliderBuilder.() -> Unit): FormSliderElement {
    val element = SliderBuilder(tag).apply(init).build()
    element.id = ++lastId
    elements.add(element)
    return element
}

/** Builder method to add a FormTextViewElement */
class TextViewBuilder(tag: Int) : BaseElementBuilder<String>(tag) {
    override fun build() =
            (FormTextViewElement(tag)
                    .setTitle(title.orEmpty())
                    .setValue(value)
                    .setVisible(visible)
                    as FormTextViewElement)
}

fun FormBuildHelper.textView(tag: Int, init: TextViewBuilder.() -> Unit): FormTextViewElement {
    val element = TextViewBuilder(tag).apply(init).build()
    element.id = ++lastId
    elements.add(element)
    return element
}


