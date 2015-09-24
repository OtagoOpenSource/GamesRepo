using UnityEngine;
using System.Collections;


[RequireComponent(typeof(Rigidbody))]

public class Gravity : MonoBehaviour {

	const float G = 0.01f;

	Rigidbody m_Rigidbody;

	Rigidbody p_Rigidbody;

	public Vector3 initialVelocity = new Vector3(0f,0f,0f);

	// Use this for initialization
	void Start () {
		m_Rigidbody = GetComponent<Rigidbody>();

		p_Rigidbody = transform.parent.GetComponent<Rigidbody> ();

		//initialVelocity is taken to be relative to parent
		//m_Rigidbody.velocity = transform.parent.TransformVector (initialVelocity);

		Component[] bodies = GetComponentsInChildren<Rigidbody> ();
		foreach (Rigidbody body in bodies) {
			body.velocity += transform.parent.TransformDirection (initialVelocity);
		}

	}
	
	// Update is called once per frame
	void FixedUpdate () {
		Vector3 r = transform.parent.position-transform.position;
			
		Vector3 g = r.normalized * (G * p_Rigidbody.mass / r.sqrMagnitude);

		//m_Rigidbody.AddForce(g, ForceMode.Acceleration);
		//float magAcc = G * mass / (denom * denom);




	}
}
