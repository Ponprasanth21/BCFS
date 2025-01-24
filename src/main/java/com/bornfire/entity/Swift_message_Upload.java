package com.bornfire.entity;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.bornfire.entity.BIPS_SWIFT_MT_MSG;


@Service
@Transactional
public class Swift_message_Upload {

	
	@Autowired
	SessionFactory sessionFactory;

	
	
	@Autowired
	BIPS_SWIFT_MT_MSG  bips_swift_mt_msg;
	
	public String addPARAMETER(BIPS_SWIFT_MT_MSG bips_swift_mt_msg, String formmode) {
		Session hs = sessionFactory.getCurrentSession();
		String msg = "";
		/* try { */
		if (formmode.equals("add")) {
			BIPS_SWIFT_MT_MSG up = bips_swift_mt_msg;
			
			hs.save(up);
			System.out.println("Output---->saving-------");
			msg = "Added Successfully";
			
		}
		return msg;
	}
}
