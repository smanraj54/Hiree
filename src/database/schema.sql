
-- Candidate table
CREATE TABLE IF NOT EXISTS Candidate (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        email VARCHAR(255) NOT NULL UNIQUE,
        username VARCHAR(255) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL,
        status BOOLEAN DEFAULT false,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- CandidateDetails table
CREATE TABLE IF NOT EXISTS CandidateDetails (
        id INT AUTO_INCREMENT PRIMARY KEY,
        candidate_id INT,
        DOB VARCHAR(20),
        about_me VARCHAR(1000) NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Recruiter table
CREATE TABLE IF NOT EXISTS Recruiter (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        email VARCHAR(255) NOT NULL,
        username VARCHAR(255) NOT NULL,
        password VARCHAR(255) NOT NULL,
        status BOOLEAN DEFAULT false,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- RecruiterDetails table
CREATE TABLE IF NOT EXISTS RecruiterDetails (
        id INT AUTO_INCREMENT PRIMARY KEY,
        recruiter_id INT,
        DOJ VARCHAR(20),
        company_details VARCHAR(1000) NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Job table
CREATE TABLE IF NOT EXISTS Job (
        id INT AUTO_INCREMENT PRIMARY KEY,
        recruiter_id INT,
        job_headline VARCHAR(200) NOT NULL,
        job_description VARCHAR(1000) NOT NULL,
        date_of_posting VARCHAR(30),
        is_active BOOLEAN DEFAULT true,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- AppliedJob table
CREATE TABLE IF NOT EXISTS AppliedJob (
        id INT AUTO_INCREMENT PRIMARY KEY,
        job_id INT,
        candidate_id INT,
        date_of_application TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        result VARCHAR(20) DEFAULT 'Pending',
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE hireedb.AppliedJob ADD CONSTRAINT uniq UNIQUE(job_id, candidate_id);


