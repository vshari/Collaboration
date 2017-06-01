package com.niit.collaboration.backend.daoimpl;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.collaboration.backend.dao.FileUploadDAO;
import com.niit.collaboration.backend.model.UploadFile;


@Repository
public class FileUploadDAOImpl  implements FileUploadDAO{
	
	@Autowired
	private SessionFactory sessionFactory;

	 public FileUploadDAOImpl() {
		// TODO Auto-generated constructor stub
	}
	 
	 public FileUploadDAOImpl(SessionFactory sessionFactory) {
			this.sessionFactory = sessionFactory;
		}
	 
	
	 @Transactional
	public void save(UploadFile uploadFile) {
		 Session session = sessionFactory.openSession();
			session.beginTransaction();

			session.save(uploadFile);
			session.getTransaction().commit();
			session.flush();
			session.close();
		
		
		
	}

	public UploadFile getFile(String username) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from UploadFile where username=?");
		query.setParameter(0, username);
		UploadFile uploadFile = (UploadFile) query.uniqueResult();
		session.close();
		return uploadFile;
		
	
	}

}
