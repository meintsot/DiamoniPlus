INSERT INTO diamoni_plus_user
(
    username,
    email,
    password,
    first_name,
    last_name,
    role_type,
    phone,
    is_host_approved,
    average_reviews,
    total_reviews,
    created_at,
    updated_at
)
VALUES
    (
        'admin', -- Username
        'admin@example.com', -- Email
        '$2a$10$JNgmmE1XnHYag7LvkAvCIu74UIioG/e/8/8frQ/3zwQ1Xn1xqHR4C', -- Password Hashed
        'Admin', -- First Name
        'Admin', -- Last Name
        'ADMIN', -- Role Type
        '1234567890', -- Phone
        true, -- Is Host Approved: Set as needed
        0.0, -- Average Reviews: Set as needed
        0, -- Total Reviews: Set as needed
        CURRENT_TIMESTAMP, -- Created At
        CURRENT_TIMESTAMP  -- Updated At
    );
