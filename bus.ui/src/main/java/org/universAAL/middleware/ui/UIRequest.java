/*	
	Copyright 2008-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer Gesellschaft - Institut fuer Graphische Datenverarbeitung 
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either.ss or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.middleware.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.universAAL.middleware.owl.supply.AbsLocation;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.owl.AccessImpairment;
import org.universAAL.middleware.ui.owl.DialogType;
import org.universAAL.middleware.ui.owl.Gender;
import org.universAAL.middleware.ui.owl.Modality;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;

/**
 * Instances of this class can be used to user interaction requests.
 * Applications just need to provide the dialog's {@link Form} object along with
 * the addressed user, the priority of the dialog, and the language and privacy
 * level of the content. The UI bus will then add the current adaptation
 * parameters to the call (by asking the Dialog Manager) before selecting the
 * appropriate UI handler and forwarding the call to it.
 * 
 * @author mtazari
 * @author Carsten Stockloew
 */
public class UIRequest extends Resource {

    /** The Constant uAAL_UI_NAMESPACE. */
    public static final String uAAL_UI_NAMESPACE = uAAL_NAMESPACE_PREFIX
	    + "UI.owl#";

    /** The Constant MY_URI. */
    public static final String MY_URI = uAAL_UI_NAMESPACE + "UIRequest";

    /** The Constant PROP_ADDRESSED_USER. */
    public static final String PROP_ADDRESSED_USER = uAAL_UI_NAMESPACE
	    + "addressedUser";

    /** The Constant PROP_DIALOG_FORM. */
    public static final String PROP_DIALOG_FORM = uAAL_UI_NAMESPACE
	    + "dialogForm";

    /** The Constant PROP_DIALOG_PRIORITY. */
    public static final String PROP_DIALOG_PRIORITY = uAAL_UI_NAMESPACE
	    + "dialogPriority";

    /** The Constant PROP_HAS_ACCESS_IMPAIRMENT. */
    public static final String PROP_HAS_ACCESS_IMPAIRMENT = uAAL_UI_NAMESPACE
	    + "hasAccessImpairment";

    /** The Constant PROP_DIALOG_LANGUAGE. */
    public static final String PROP_DIALOG_LANGUAGE = uAAL_UI_NAMESPACE
	    + "dialogLanguage";

    /** The Constant PROP_PRESENTATION_MODALITY. */
    public static final String PROP_PRESENTATION_MODALITY = uAAL_UI_NAMESPACE
	    + "presentationModality";

    /** The Constant PROP_PRESENTATION_MODALITY_ALT. */
    public static final String PROP_PRESENTATION_MODALITY_ALT = uAAL_UI_NAMESPACE
	    + "altPresentationModality";

    /** The Constant PROP_PRESENTATION_LOCATION. */
    public static final String PROP_PRESENTATION_LOCATION = uAAL_UI_NAMESPACE
	    + "presentationLocation";

    /** The Constant PROP_DIALOG_PRIVACY_LEVEL. */
    public static final String PROP_DIALOG_PRIVACY_LEVEL = uAAL_UI_NAMESPACE
	    + "dialogPrivacyLevel";

    /** The Constant PROP_SCREEN_RESOLUTION_MAX_X. */
    public static final String PROP_SCREEN_RESOLUTION_MAX_X = uAAL_UI_NAMESPACE
	    + "screenResolutionMaxX";

    /** The Constant PROP_SCREEN_RESOLUTION_MAX_Y. */
    public static final String PROP_SCREEN_RESOLUTION_MAX_Y = uAAL_UI_NAMESPACE
	    + "screenResolutionMaxY";

    /** The Constant PROP_SCREEN_RESOLUTION_MIN_X. */
    public static final String PROP_SCREEN_RESOLUTION_MIN_X = uAAL_UI_NAMESPACE
	    + "screenResolutionMinX";

    /** The Constant PROP_SCREEN_RESOLUTION_MIN_Y. */
    public static final String PROP_SCREEN_RESOLUTION_MIN_Y = uAAL_UI_NAMESPACE
	    + "screenResolutionMinY";

    /** The Constant PROP_VOICE_GENDER. */
    public static final String PROP_VOICE_GENDER = uAAL_UI_NAMESPACE
	    + "voiceGander";

    /** The Constant PROP_VOICE_LEVEL. */
    public static final String PROP_VOICE_LEVEL = uAAL_UI_NAMESPACE
	    + "voiceLevel";

    /**
     * This constructor is for the exclusive usage by deserializers.
     */
    public UIRequest() {
	super();
    }

    /**
     * Instantiates a new UI request.
     * 
     * @param user
     *            the user
     * @param dialogForm
     *            the dialog form
     * @param dialogPriority
     *            the dialog priority
     * @param dialogLang
     *            the dialog lang
     * @param dialogPrivacy
     *            the dialog privacy
     */
    public UIRequest(Resource user, Form dialogForm,
	    LevelRating dialogPriority, Locale dialogLang,
	    PrivacyLevel dialogPrivacy) {
	super();

	addType(MY_URI, true);
	props.put(PROP_ADDRESSED_USER, user);
	props.put(PROP_DIALOG_FORM, dialogForm);
	props.put(PROP_DIALOG_PRIORITY,
		(dialogPriority == null) ? LevelRating.low : dialogPriority);
	props.put(PROP_DIALOG_LANGUAGE, dialogLang);
	props.put(PROP_DIALOG_PRIVACY_LEVEL, dialogPrivacy);
    }

    /**
     * Gets the addressed user.
     * 
     * @return the addressed user
     */
    public Resource getAddressedUser() {
	return (Resource) props.get(PROP_ADDRESSED_USER);
    }

    /**
     * Gets the alt presentation modality.
     * 
     * @return the alt presentation modality
     */
    public Modality getAltPresentationModality() {
	return (Modality) props.get(PROP_PRESENTATION_MODALITY_ALT);
    }

    /**
     * Gets the dialog form.
     * 
     * @return the dialog form
     */
    public Form getDialogForm() {
	return (Form) props.get(PROP_DIALOG_FORM);
    }

    /**
     * Gets the dialog id.
     * 
     * @return the dialog id
     */
    public String getDialogID() {
	Form f = getDialogForm();
	return (f == null) ? null : f.getDialogID();
    }

    /**
     * Gets the dialog language.
     * 
     * @return the dialog language
     */
    public Locale getDialogLanguage() {
	return (Locale) props.get(PROP_DIALOG_LANGUAGE);
    }

    /**
     * Gets the dialog priority.
     * 
     * @return the dialog priority
     */
    public LevelRating getDialogPriority() {
	return (LevelRating) props.get(PROP_DIALOG_PRIORITY);
    }

    /**
     * Gets the dialog privacy level.
     * 
     * @return the dialog privacy level
     */
    public PrivacyLevel getDialogPrivacyLevel() {
	return (PrivacyLevel) props.get(PROP_DIALOG_PRIVACY_LEVEL);
    }

    /**
     * Gets the dialog type.
     * 
     * @return the dialog type
     */
    public DialogType getDialogType() {
	Form f = getDialogForm();
	return (f == null) ? null : f.getDialogType();
    }

    /**
     * Gets the impairments.
     * 
     * @return the impairments
     */
    public AccessImpairment[] getImpairments() {
	List l = (List) props.get(PROP_HAS_ACCESS_IMPAIRMENT);
	return (l == null) ? null : (AccessImpairment[]) l
		.toArray(new AccessImpairment[l.size()]);
    }

    /**
     * Gets the presentation location.
     * 
     * @return the presentation location
     */
    public AbsLocation getPresentationLocation() {
	return (AbsLocation) props.get(PROP_PRESENTATION_LOCATION);
    }

    /**
     * Gets the presentation modality.
     * 
     * @return the presentation modality
     */
    public Modality getPresentationModality() {
	return (Modality) props.get(PROP_PRESENTATION_MODALITY);
    }

    /**
     * @see org.universAAL.middleware.rdf.Resource#getPropSerializationType(java.lang.String)
     */
    public int getPropSerializationType(String propURI) {
	return PROP_DIALOG_FORM.equals(propURI) ? PROP_SERIALIZATION_FULL
		: PROP_SERIALIZATION_REDUCED;
    }

    /**
     * Gets the screen resolution max x.
     * 
     * @return the screen resolution max x
     */
    public int getScreenResolutionMaxX() {
	Integer i = (Integer) props.get(PROP_SCREEN_RESOLUTION_MAX_X);
	return (i == null) ? -1 : i.intValue();
    }

    /**
     * Gets the screen resolution max y.
     * 
     * @return the screen resolution max y
     */
    public int getScreenResolutionMaxY() {
	Integer i = (Integer) props.get(PROP_SCREEN_RESOLUTION_MAX_Y);
	return (i == null) ? -1 : i.intValue();
    }

    /**
     * Gets the screen resolution min x.
     * 
     * @return the screen resolution min x
     */
    public int getScreenResolutionMinX() {
	Integer i = (Integer) props.get(PROP_SCREEN_RESOLUTION_MIN_X);
	return (i == null) ? -1 : i.intValue();
    }

    /**
     * Gets the screen resolution min y.
     * 
     * @return the screen resolution min y
     */
    public int getScreenResolutionMinY() {
	Integer i = (Integer) props.get(PROP_SCREEN_RESOLUTION_MIN_Y);
	return (i == null) ? -1 : i.intValue();
    }

    /**
     * Gets the voice gender.
     * 
     * @return the voice gender
     */
    public Gender getVoiceGender() {
	return (Gender) props.get(PROP_VOICE_GENDER);
    }

    /**
     * Gets the voice level.
     * 
     * @return the voice level
     */
    public int getVoiceLevel() {
	Integer i = (Integer) props.get(PROP_VOICE_LEVEL);
	return (i == null) ? -1 : i.intValue();
    }

    /**
     * Sets the alt presentation modality.
     * 
     * @param outputModality
     *            the new alt presentation modality
     */
    public void setAltPresentationModality(Modality outputModality) {
	if (outputModality != null
		&& !props.containsKey(PROP_PRESENTATION_MODALITY_ALT))
	    props.put(PROP_PRESENTATION_MODALITY_ALT, outputModality);
    }

    /**
     * Sets the collected input.
     * 
     * @param data
     *            the new collected input
     */
    public void setCollectedInput(Resource data) {
	Form f = getDialogForm();
	if (f != null)
	    f.substituteData(data);
    }

    /**
     * Sets the impairments.
     * 
     * @param impairments
     *            the new impairments
     */
    public void setImpairments(AccessImpairment[] impairments) {
	if (impairments != null && impairments.length > 0
		&& !props.containsKey(PROP_HAS_ACCESS_IMPAIRMENT))
	    props.put(PROP_HAS_ACCESS_IMPAIRMENT, Arrays.asList(impairments));
    }

    /**
     * Sets the presentation location.
     * 
     * @param presentationLocation
     *            the new presentation location
     */
    public void setPresentationLocation(AbsLocation presentationLocation) {
	if (presentationLocation != null
		&& !props.containsKey(PROP_PRESENTATION_LOCATION))
	    props.put(PROP_PRESENTATION_LOCATION, presentationLocation);
    }

    /**
     * Sets the presentation modality.
     * 
     * @param outputModality
     *            the new presentation modality
     */
    public void setPresentationModality(Modality outputModality) {
	if (outputModality != null
		&& !props.containsKey(PROP_PRESENTATION_MODALITY))
	    props.put(PROP_PRESENTATION_MODALITY, outputModality);
    }

    /**
     * Sets the privacy mapping.
     * 
     * @param pl
     *            the new privacy mapping
     */
    public void setPrivacyMapping(PrivacyLevel pl) {
	if (props.containsKey(PROP_DIALOG_PRIVACY_LEVEL)
		&& (pl == PrivacyLevel.insensible || pl == PrivacyLevel.personal))
	    props.put(PROP_DIALOG_PRIVACY_LEVEL, pl);
    }

    /**
     * Sets the screen resolution max x.
     * 
     * @param x
     *            the new screen resolution max x
     */
    public void setScreenResolutionMaxX(int x) {
	if (x > 0 && !props.containsKey(PROP_SCREEN_RESOLUTION_MAX_X))
	    props.put(PROP_SCREEN_RESOLUTION_MAX_X, new Integer(x));
    }

    /**
     * Sets the screen resolution max y.
     * 
     * @param y
     *            the new screen resolution max y
     */
    public void setScreenResolutionMaxY(int y) {
	if (y > 0 && !props.containsKey(PROP_SCREEN_RESOLUTION_MAX_Y))
	    props.put(PROP_SCREEN_RESOLUTION_MAX_Y, new Integer(y));
    }

    /**
     * Sets the screen resolution min x.
     * 
     * @param x
     *            the new screen resolution min x
     */
    public void setScreenResolutionMinX(int x) {
	if (x > 0 && !props.containsKey(PROP_SCREEN_RESOLUTION_MIN_X))
	    props.put(PROP_SCREEN_RESOLUTION_MIN_X, new Integer(x));
    }

    /**
     * Sets the screen resolution min y.
     * 
     * @param y
     *            the new screen resolution min y
     */
    public void setScreenResolutionMinY(int y) {
	if (y > 0 && !props.containsKey(PROP_SCREEN_RESOLUTION_MIN_Y))
	    props.put(PROP_SCREEN_RESOLUTION_MIN_Y, new Integer(y));
    }

    /**
     * Sets the voice gender.
     * 
     * @param g
     *            the new voice gender
     */
    public void setVoiceGender(Gender g) {
	if (g != null && !props.containsKey(PROP_VOICE_GENDER))
	    props.put(PROP_VOICE_GENDER, g);
    }

    /**
     * Sets the voice level.
     * 
     * @param loudnessPercentage
     *            the new voice level
     */
    public void setVoiceLevel(int loudnessPercentage) {
	if (loudnessPercentage > -1 && loudnessPercentage < 101
		&& !props.containsKey(PROP_VOICE_LEVEL))
	    props.put(PROP_VOICE_LEVEL, new Integer(loudnessPercentage));
    }
}