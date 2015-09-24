using UnityEngine;
using System.Collections;


[RequireComponent(typeof(Rigidbody))]

public class Gravity : MonoBehaviour {

	const float G = 1f;

	Rigidbody m_Rigidbody;

	Rigidbody p_Rigidbody;
	Transform p_Transform;

	public Vector3 initialVelocity = new Vector3(0f,0f,0f);

	// Use this for initialization
	void Start () {
		m_Rigidbody = GetComponent<Rigidbody>();

		p_Rigidbody = GetComponentInParent<Rigidbody> ();
		p_Transform = GetComponentInParent<Transform> ();

		//initialVelocity is taken to be relative to parent
		m_Rigidbody.velocity = p_Transform.TransformVector (initialVelocity);


	}
	
	// Update is called once per frame
	void FixedUpdate () {
		float denom = transform.localPosition.magnitude;
		//float magAcc = G * mass / (denom * denom);




	}
}
