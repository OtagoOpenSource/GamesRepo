using UnityEngine;

namespace UnityStandardAssets.Characters.ThirdPerson
{
	[RequireComponent(typeof(Rigidbody))]
	[RequireComponent(typeof(CapsuleCollider))]
	[RequireComponent(typeof(Animator))]
	public class ThirdPersonCharacter : MonoBehaviour
	{
		[SerializeField] float m_MovingTurnSpeed = 360;
		[SerializeField] float m_StationaryTurnSpeed = 180;
		[SerializeField] float m_JumpPower = 12f;
		[Range(1f, 4f)][SerializeField] float m_GravityMultiplier = 2f;
		[SerializeField] float m_RunCycleLegOffset = 0.2f; //specific to the character in sample assets, will need to be modified to work with others
		[SerializeField] float m_MoveSpeedMultiplier = 1f;
		[SerializeField] float m_AnimSpeedMultiplier = 1f;
		[SerializeField] float m_GroundCheckDistance = 0.1f;

		Rigidbody m_Rigidbody;
		Animator m_Animator;
		bool m_IsGrounded;
		float m_OrigGroundCheckDistance;
		const float k_Half = 0.5f;
		float m_TurnAmount;
		float m_ForwardAmount;
		Vector3 m_GroundNormal;
		float m_CapsuleHeight;
		Vector3 m_CapsuleCenter;
		CapsuleCollider m_Capsule;
		bool m_Crouching;
		//Vector3 gravityDirection = new Vector3(-1,0,0);
		Vector3 gravityDirection;
		Quaternion rot;
		Quaternion rot2;

		Vector3 planetoidUp;
		Vector3 planetoidDown;
		Vector3 planetoidForward;
		Vector3 planetoidRight;
		Vector3 planetoidVelocity;

		void Start()
		{
			m_Animator = GetComponent<Animator>();
			m_Rigidbody = GetComponent<Rigidbody>();
			m_Capsule = GetComponent<CapsuleCollider>();
			m_CapsuleHeight = m_Capsule.height;
			m_CapsuleCenter = m_Capsule.center;

			//m_Rigidbody.constraints = RigidbodyConstraints.FreezeRotationX | RigidbodyConstraints.FreezeRotationY | RigidbodyConstraints.FreezeRotationZ;
			m_OrigGroundCheckDistance = m_GroundCheckDistance;

			gravityDirection = -transform.localPosition.normalized;
			//rot  = Quaternion.FromToRotation (Vector3.down, gravityDirection);
			//transform.rotation = rot;
		}

		void FixedUpdate() {

			gravityDirection = -transform.localPosition.normalized;
			rot  = Quaternion.FromToRotation (Vector3.down, gravityDirection);


			Quaternion rot3 = Quaternion.Inverse (transform.rotation)*rot;
			//Vector3 angles = rot.eulerAngles - transform.rotation.eulerAngles;

			//Debug.Log ("A:" + angles.ToString ("F4"));
			transform.Rotate (rot3.eulerAngles, Space.World);


			//transform.rotation = rot;
			//rot2 = new Quaternion (0f, 0f, 0f, 1f);

			//Debug.Log ("G:" + gravityDirection.ToString("F4"));
			//Debug.Log ("D:" + Vector3.down.ToString("F4"));
			//Debug.Log ("R:" + rot.eulerAngles.ToString ("F4"));
			//transform//trans


			//transform.rotation = rot;
			//transform.Rotate (rot.eulerAngles,Space.World);
			planetoidUp = -gravityDirection;
			planetoidDown = gravityDirection;
			planetoidForward = (rot * Vector3.forward).normalized;
			planetoidRight = (rot * Vector3.right).normalized;

			planetoidVelocity.x = Vector3.Dot (m_Rigidbody.velocity,planetoidRight);
			planetoidVelocity.y = Vector3.Dot (m_Rigidbody.velocity,planetoidForward);
			planetoidVelocity.z = Vector3.Dot (m_Rigidbody.velocity,planetoidUp);

			//rot = Quaternion.FromToRotation (Vector3.down, gravityDirection);
			//Debug.Log ("UP:" + Vector3.up.ToString ("F4"));
			//Vector3 z = rot * Vector3.up;
			//Debug.Log ("z:" +z.ToString ("F4"));
			Physics.gravity = gravityDirection*9.81f;
		}

		public void Move(Vector3 move, bool crouch, bool jump)
		{
		//Vector3 newGravity = -transform.localPosition.normalized;
		// convert the world relative moveInput vector into a local-relative
		// turn amount and forward amount required to head in the desired
		// direction.
		if (move.magnitude > 1f)
			move.Normalize ();



		move = transform.InverseTransformDirection (move);
		CheckGroundStatus ();

		move = rot * move;
		move = Vector3.ProjectOnPlane (move, m_GroundNormal);

		//move = transform.*move;
		//Debug.Log ("MoveAft:" + move.ToString ("F4"));
		//Debug.Log ("GroundNorm:" + m_GroundNormal.ToString ("F4"));

		float z = Vector3.Dot (move, planetoidForward);
		float x = Vector3.Dot (move, planetoidRight);
			

		//rot = Quaternion.FromToRotation (Vector3.down, gravityDirection);

		//move = rot2*move;
		m_TurnAmount = Mathf.Atan2 (x, z);
		//m_ForwardAmount = Vector3.Dot(move,rot*Vector3.forward);
		m_ForwardAmount = z;
		//Debug.Log (move.ToString ("F4"));
		//Debug.Log ("MF:" + m_ForwardAmount);

		ApplyExtraTurnRotation ();

		// control and velocity handling is different when grounded and airborne:
		if (m_IsGrounded) {
			HandleGroundedMovement (crouch, jump);
		} else {
			HandleAirborneMovement ();
		}

		ScaleCapsuleForCrouching (crouch);
		PreventStandingInLowHeadroom ();

		//move = rot*move;
			
			
		#if UNITY_EDITOR
		if (move.magnitude > 0) {
			//Debug.Log ("f:" + m_ForwardAmount + ", t:" + m_TurnAmount);
			//Debug.DrawLine(transform.position, transform.position + move * 2, Color.red);
			//Debug.Log ("MoveBef:" + move.ToString ("F4"));
			//Debug.DrawLine(transform.position, transform.position + move * 5, Color.blue);
		}
		#endif

		//if (move.magnitude > 0) {
				Vector3 zt = transform.TransformPoint(Vector3.forward);
				Vector3 gt = transform.TransformPoint (planetoidForward);

				Debug.Log ("T:" +zt.ToString ("F4"));
				Debug.Log ("G:" +gt.ToString ("F4"));
		//	}
	
			//transform.Rotate (Quaternion.FromToRotation (zt,gt).eulerAngles, Space.World);
			//transform.rotation *= rot2;


			//}
			// send input and other state parameters to the animator
			UpdateAnimator(move);
			//transform.rotation = rot*rot2;
			//if (m_TurnAmount > 0) {
				//Debug.Log ("Rot2:" + rot2.ToString ("F4"));
			//}
		}


		void ScaleCapsuleForCrouching(bool crouch)
		{
			if (m_IsGrounded && crouch)
			{
				if (m_Crouching) return;
				m_Capsule.height = m_Capsule.height / 2f;
				m_Capsule.center = m_Capsule.center / 2f;
				m_Crouching = true;
			}
			else
			{
				Ray crouchRay = new Ray(m_Rigidbody.position + planetoidUp * m_Capsule.radius * k_Half, planetoidUp);
				float crouchRayLength = m_CapsuleHeight - m_Capsule.radius * k_Half;
				if (Physics.SphereCast(crouchRay, m_Capsule.radius * k_Half, crouchRayLength))
				{
					m_Crouching = true;
					return;
				}
				m_Capsule.height = m_CapsuleHeight;
				m_Capsule.center = m_CapsuleCenter;
				m_Crouching = false;
			}
		}

		void PreventStandingInLowHeadroom()
		{
			// prevent standing up in crouch-only zones
			if (!m_Crouching)
			{
				Ray crouchRay = new Ray(m_Rigidbody.position + planetoidUp * m_Capsule.radius * k_Half, planetoidUp);
				float crouchRayLength = m_CapsuleHeight - m_Capsule.radius * k_Half;
				if (Physics.SphereCast(crouchRay, m_Capsule.radius * k_Half, crouchRayLength))
				{
					m_Crouching = true;
				}
			}
		}


		void UpdateAnimator(Vector3 move)
		{
			// update the animator parameters
			m_Animator.SetFloat("Forward", m_ForwardAmount, 0.1f, Time.deltaTime);
			m_Animator.SetFloat("Turn", m_TurnAmount, 0.1f, Time.deltaTime);
			m_Animator.SetBool("Crouch", m_Crouching);
			m_Animator.SetBool("OnGround", m_IsGrounded);
			if (!m_IsGrounded)
			{
				m_Animator.SetFloat("Jump", planetoidVelocity.y);
			}

			// calculate which leg is behind, so as to leave that leg trailing in the jump animation
			// (This code is reliant on the specific run cycle offset in our animations,
			// and assumes one leg passes the other at the normalized clip times of 0.0 and 0.5)
			float runCycle =
				Mathf.Repeat(
					m_Animator.GetCurrentAnimatorStateInfo(0).normalizedTime + m_RunCycleLegOffset, 1);
			float jumpLeg = (runCycle < k_Half ? 1 : -1) * m_ForwardAmount;
			if (m_IsGrounded)
			{
				m_Animator.SetFloat("JumpLeg", jumpLeg);
			}

			// the anim speed multiplier allows the overall speed of walking/running to be tweaked in the inspector,
			// which affects the movement speed because of the root motion.
			if (m_IsGrounded && move.magnitude > 0)
			{
				m_Animator.speed = m_AnimSpeedMultiplier;
			}
			else
			{
				// don't use that while airborne
				m_Animator.speed = 1;
			}
		}


		void HandleAirborneMovement()
		{
			// apply extra gravity from multiplier:
			Vector3 extraGravityForce = (gravityDirection * m_GravityMultiplier) - gravityDirection;
			m_Rigidbody.AddForce(extraGravityForce);

			m_GroundCheckDistance = planetoidVelocity.y < 0 ? m_OrigGroundCheckDistance : 0.01f;
		}


		void HandleGroundedMovement(bool crouch, bool jump)
		{
			// check whether conditions are right to allow a jump:
			if (jump && !crouch && m_Animator.GetCurrentAnimatorStateInfo(0).IsName("Grounded"))
			{
				//Vector3 newVel = m_Rigidbody.velocity-planetoidVelocity.y*planetoidUp;
				m_Rigidbody.velocity = planetoidVelocity.x*planetoidRight +
						               m_JumpPower*planetoidUp +
									   planetoidVelocity.z*planetoidForward;
				// jump!
				//m_Rigidbody.velocity = new Vector3(m_Rigidbody.velocity.x, m_JumpPower, m_Rigidbody.velocity.z);
				m_IsGrounded = false;
				m_Animator.applyRootMotion = false;
				m_GroundCheckDistance = 0.1f;
			}
		}

		void ApplyExtraTurnRotation()
		{
			// help the character turn faster (this is in addition to root rotation in the animation)
			float turnSpeed = Mathf.Lerp(m_StationaryTurnSpeed, m_MovingTurnSpeed, m_ForwardAmount);
			transform.Rotate(0, m_TurnAmount * turnSpeed * Time.deltaTime, 0);
			//Debug.Log ("turnSpeed:" + (m_TurnAmount * turnSpeed * Time.deltaTime));
			//transform.RotateAround (transform.position, planetoidUp, m_TurnAmount * turnSpeed * Time.deltaTime);
			//transform.localRotation = Quaternion.FromToRotation(
			//rot2 = Quaternion.AngleAxis(m_TurnAmount * turnSpeed * Time.deltaTime, planetoidDown);
			//transform.Rotate (Vector3(0,
		}


		public void OnAnimatorMove()
		{
			// we implement this function to override the default root motion.
			// this allows us to modify the positional speed before it's applied.
			if (m_IsGrounded && Time.deltaTime > 0)
			{
				Vector3 v = (m_Animator.deltaPosition * m_MoveSpeedMultiplier) / Time.deltaTime;

				// we preserve the existing y part of the current velocity.
				float upSpeed = Vector3.Dot (v,planetoidUp);
				if(upSpeed > 0) {
					v -= upSpeed*planetoidUp;
				}
				//v = m_Rigidbody.velocity.y;
				m_Rigidbody.velocity = v;
			}
		}


		void CheckGroundStatus()
		{
			RaycastHit hitInfo;
			//Vector3 newGravity = -transform.localPosition.normalized;
			//if (newGravity.magnitude < 1) {
			//Vector3	newGravity = Vector3.up;
			//}
			//Debug.Log (newGravity.ToString ("F4"));


			//Debug.Log ("UP:" + Vector3.up.ToString ("F4"));
			//Debug.Log ("DW:" + Vector3.down.ToString ("F4"));

			//Vector3 planetoidUp = Vector3.up;
			//Vector3 planetoidDown = Vector3.down;
			//planetoidUp = -planetoidUp;
			//planetoidDown = -planetoidDown;


#if UNITY_EDITOR
			//Vector3 test1 = transform.position + (planetoidUp * 0.1f);
			//Vector3 test2 = transform.position + (planetoidUp * 0.1f) + (planetoidDown * m_GroundCheckDistance);
			//Debug.Log ("1: " + test1.ToString ("F4"));
			//Debug.Log ("2: " + test2.ToString ("F4"));


			// helper to visualise the ground check ray in the scene view
			//Debug.DrawLine(transform.position + (Vector3.up * 0.1f), transform.position + (Vector3.up * 0.1f) + (Vector3.down * m_GroundCheckDistance));
			Debug.DrawLine(transform.position + (planetoidUp * 0.1f), transform.position + (planetoidDown * m_GroundCheckDistance));
#endif
			// 0.1f is a small offset to start the ray from inside the character
			// it is also good to note that the transform position in the sample assets is at the base of the character
			if (Physics.Raycast(transform.position + (planetoidUp * 0.1f), planetoidDown, out hitInfo, m_GroundCheckDistance))
			{
				//m_GroundNormal = rot*hitInfo.normal;
				m_GroundNormal = hitInfo.normal;
				m_IsGrounded = true;
				m_Animator.applyRootMotion = true;
			}
			else
			{
				m_IsGrounded = false;
				m_GroundNormal = -gravityDirection;
				m_Animator.applyRootMotion = false;
			}
		}
	}
}
