package com.signature;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Collections;

import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Generate the enveloped document
 * 
 * @author André Valadas
 */
public class GenerateSignatureTest {

	/**
	 * Generate XML Digital Signature
	 */
	@Test
	public void testGenerateSingnature() {
		try {
			// Create a DOM XMLSignatureFactory that will be used to generate the
	        // enveloped signature
	        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

	        // Create a Reference to the enveloped document (in this case we are
	        // signing the whole document, so a URI of "" signifies that) and
	        // also specify the SHA1 digest algorithm and the ENVELOPED Transform.
	        Reference ref = fac.newReference
	        		  ("", fac.newDigestMethod(DigestMethod.SHA1, null),
	        		    Collections.singletonList
	        		      (fac.newTransform(Transform.ENVELOPED,
	        		        (TransformParameterSpec) null)), null, null);

	        // Create the SignedInfo
	        SignedInfo si = fac.newSignedInfo
	        		  (fac.newCanonicalizationMethod
	        		    (CanonicalizationMethod.INCLUSIVE_WITH_COMMENTS,
	        		      (C14NMethodParameterSpec) null),
	        		    fac.newSignatureMethod(SignatureMethod.DSA_SHA1, null),
	        		    Collections.singletonList(ref)); 

	        // Create a DSA KeyPair
	        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
	        kpg.initialize(512);
	        KeyPair kp = kpg.generateKeyPair(); 

	        // Create a KeyValue containing the DSA PublicKey that was generated
	        KeyInfoFactory kif = fac.getKeyInfoFactory(); 
	        KeyValue kv = kif.newKeyValue(kp.getPublic());

	        // Create a KeyInfo and add the KeyValue to it
	        KeyInfo ki = kif.newKeyInfo(Collections.singletonList(kv));

	        final String xmlSignature = ClassLoader.getSystemResource(Validate.inputDocument).getPath(); 

	        // Instantiate the document to be validated
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        dbf.setNamespaceAware(true);
	        Document doc = dbf.newDocumentBuilder().parse(new FileInputStream(xmlSignature));

	        // Find Signature element
	        NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
			if (nl.getLength() > 0) {
				// Remove Signature Nodes
				for (int i = nl.getLength() - 1; i >= 0; i--) {
					Element e = (Element) nl.item(i);
					e.getParentNode().removeChild(e);
				}
			}

	        // Creating a Signing Context
	        DOMSignContext dsc = new DOMSignContext(kp.getPrivate(), doc.getDocumentElement());

	        // Create the XMLSignature (but don't sign it yet)
	        XMLSignature signature = fac.newXMLSignature(si, ki);

	        // Marshal, generate (and sign) the enveloped signature
	        signature.sign(dsc);

	        // Output the resulting document
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer trans = tf.newTransformer();
	        trans.transform(new DOMSource(doc), new StreamResult(new File(Validate.outputSignedDocument))); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}