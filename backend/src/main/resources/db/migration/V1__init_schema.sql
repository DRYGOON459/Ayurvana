-- AyurVana Classical Ayurvedic Medicine & Healthcare Platform
-- Enterprise PostgreSQL DDL Schema Migration (V1__init_schema.sql)

-- ==========================================
-- 1. Authentication, RBAC & User Management
-- ==========================================
CREATE TABLE IF NOT EXISTS roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    avatar_url VARCHAR(500),
    dosha_type VARCHAR(30),
    bio TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    is_verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS password_reset_tokens (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    token VARCHAR(100) NOT NULL UNIQUE,
    expiry_date TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- ==========================================
-- 2. Doctor Practitioners & Verification
-- ==========================================
CREATE TABLE IF NOT EXISTS doctor_profiles (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    qualifications VARCHAR(255) NOT NULL,
    medical_license_number VARCHAR(100) NOT NULL UNIQUE,
    state_medical_council VARCHAR(150),
    years_of_experience INTEGER NOT NULL,
    primary_specialization VARCHAR(150) NOT NULL,
    languages_spoken VARCHAR(255),
    consultation_fee NUMERIC(10, 2) NOT NULL,
    bio TEXT,
    is_verified BOOLEAN DEFAULT FALSE,
    verification_status VARCHAR(50) DEFAULT 'PENDING',
    average_rating NUMERIC(3, 2) DEFAULT 5.00,
    total_reviews INTEGER DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS doctor_availabilities (
    id BIGSERIAL PRIMARY KEY,
    doctor_id BIGINT NOT NULL REFERENCES doctor_profiles(id) ON DELETE CASCADE,
    day_of_week VARCHAR(20) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    slot_duration_minutes INTEGER DEFAULT 30,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS appointments (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    doctor_id BIGINT NOT NULL REFERENCES doctor_profiles(id),
    appointment_time TIMESTAMP WITH TIME ZONE NOT NULL,
    duration_minutes INTEGER DEFAULT 30,
    status VARCHAR(50) DEFAULT 'CONFIRMED',
    chief_complaint TEXT,
    prakriti_notes TEXT,
    pulse_nadi_notes TEXT,
    tongue_jihva_notes TEXT,
    treatment_plan TEXT,
    is_paid BOOLEAN DEFAULT FALSE,
    consultation_fee NUMERIC(10, 2),
    meeting_link VARCHAR(500),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- ==========================================
-- 3. Classical Herbal Pharmacopoeia & Dravyaguna
-- ==========================================
CREATE TABLE IF NOT EXISTS herbs (
    id BIGSERIAL PRIMARY KEY,
    common_name VARCHAR(150) NOT NULL,
    sanskrit_name VARCHAR(150) NOT NULL,
    botanical_name VARCHAR(150) NOT NULL UNIQUE,
    family VARCHAR(100),
    slug VARCHAR(150) NOT NULL UNIQUE,
    rasa_taste VARCHAR(255),
    virya_potency VARCHAR(100),
    vipaka_post_digestive VARCHAR(100),
    prabhava_special_action VARCHAR(255),
    dosha_effect VARCHAR(255),
    plant_part_used VARCHAR(255),
    short_description TEXT,
    traditional_uses TEXT,
    modern_research TEXT,
    dosage_instructions TEXT,
    image_url VARCHAR(500),
    toxicity_level VARCHAR(50) DEFAULT 'SAFE',
    contraindications TEXT,
    drug_interactions TEXT,
    pregnancy_lactation_safety TEXT,
    side_effects TEXT,
    first_aid_measures TEXT,
    classical_treatise_reference VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- ==========================================
-- 4. Classical Formulations & E-Commerce Store
-- ==========================================
CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    sanskrit_name VARCHAR(200),
    slug VARCHAR(200) NOT NULL UNIQUE,
    category VARCHAR(50) NOT NULL,
    target_dosha VARCHAR(50) NOT NULL,
    short_description VARCHAR(500),
    full_description TEXT,
    classical_reference VARCHAR(255),
    shloka_text TEXT,
    ingredients TEXT,
    benefits TEXT,
    dosage_instructions TEXT,
    anupana_carrier VARCHAR(255),
    contraindications TEXT,
    price NUMERIC(10, 2) NOT NULL,
    discount_price NUMERIC(10, 2),
    stock_quantity INTEGER NOT NULL DEFAULT 50,
    sku VARCHAR(100) UNIQUE,
    net_weight VARCHAR(50),
    image_url VARCHAR(500),
    is_featured BOOLEAN DEFAULT FALSE,
    is_ayush_certified BOOLEAN DEFAULT TRUE,
    rating NUMERIC(3, 2) DEFAULT 4.90,
    review_count INTEGER DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS orders (
    id BIGSERIAL PRIMARY KEY,
    order_number VARCHAR(100) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL REFERENCES users(id),
    status VARCHAR(50) DEFAULT 'CONFIRMED',
    total_amount NUMERIC(10, 2) NOT NULL,
    subtotal_amount NUMERIC(10, 2) NOT NULL,
    discount_amount NUMERIC(10, 2) DEFAULT 0.00,
    tax_amount NUMERIC(10, 2) DEFAULT 0.00,
    shipping_amount NUMERIC(10, 2) DEFAULT 0.00,
    coupon_code VARCHAR(50),
    shipping_name VARCHAR(150) NOT NULL,
    shipping_phone VARCHAR(50) NOT NULL,
    shipping_address_line1 VARCHAR(255) NOT NULL,
    shipping_address_line2 VARCHAR(255),
    shipping_city VARCHAR(100) NOT NULL,
    shipping_state VARCHAR(100) NOT NULL,
    shipping_postal_code VARCHAR(20) NOT NULL,
    shipping_country VARCHAR(100) DEFAULT 'India',
    payment_method VARCHAR(50) NOT NULL,
    payment_status VARCHAR(50) DEFAULT 'PENDING',
    payment_transaction_id VARCHAR(150),
    tracking_number VARCHAR(100),
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    product_id BIGINT NOT NULL REFERENCES products(id),
    product_name VARCHAR(200) NOT NULL,
    product_sku VARCHAR(100),
    product_image_url VARCHAR(500),
    unit_price NUMERIC(10, 2) NOT NULL,
    quantity INTEGER NOT NULL,
    total_price NUMERIC(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS payment_transactions (
    id BIGSERIAL PRIMARY KEY,
    transaction_ref VARCHAR(100) NOT NULL UNIQUE,
    gateway_order_id VARCHAR(150),
    gateway_payment_id VARCHAR(150),
    user_id BIGINT NOT NULL REFERENCES users(id),
    order_id BIGINT REFERENCES orders(id),
    appointment_id BIGINT REFERENCES appointments(id),
    amount NUMERIC(10, 2) NOT NULL,
    currency VARCHAR(10) DEFAULT 'INR',
    payment_method VARCHAR(50) NOT NULL,
    payment_status VARCHAR(50) DEFAULT 'PENDING',
    signature VARCHAR(255),
    response_payload TEXT,
    error_message TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- ==========================================
-- 5. Yoga, Pranayama & Meditation
-- ==========================================
CREATE TABLE IF NOT EXISTS yoga_poses (
    id BIGSERIAL PRIMARY KEY,
    english_name VARCHAR(150) NOT NULL,
    sanskrit_name VARCHAR(150) NOT NULL,
    slug VARCHAR(150) NOT NULL UNIQUE,
    difficulty_level VARCHAR(50) NOT NULL,
    target_dosha VARCHAR(50) NOT NULL,
    category VARCHAR(100),
    instructions TEXT NOT NULL,
    benefits TEXT NOT NULL,
    contraindications TEXT,
    shloka_reference VARCHAR(255),
    duration_seconds INTEGER,
    image_url VARCHAR(500),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS meditations (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    sanskrit_title VARCHAR(150),
    slug VARCHAR(150) NOT NULL UNIQUE,
    technique VARCHAR(100) NOT NULL,
    target_dosha VARCHAR(50) NOT NULL,
    duration_minutes INTEGER NOT NULL,
    description TEXT NOT NULL,
    benefits TEXT NOT NULL,
    instructions TEXT NOT NULL,
    audio_url VARCHAR(500),
    image_url VARCHAR(500),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- ==========================================
-- 6. Classical Topics & Research Articles
-- ==========================================
CREATE TABLE IF NOT EXISTS ayurveda_topics (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    sanskrit_term VARCHAR(150),
    slug VARCHAR(150) NOT NULL UNIQUE,
    category VARCHAR(100) NOT NULL,
    summary TEXT NOT NULL,
    detailed_content TEXT NOT NULL,
    clinical_significance TEXT,
    classical_sources VARCHAR(255),
    image_url VARCHAR(500),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS articles (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    author_name VARCHAR(150) NOT NULL,
    author_credentials VARCHAR(150),
    category VARCHAR(100) NOT NULL,
    abstract_text TEXT NOT NULL,
    content TEXT NOT NULL,
    journal_citation VARCHAR(255),
    doi VARCHAR(100),
    image_url VARCHAR(500),
    read_time_minutes INTEGER DEFAULT 5,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- ==========================================
-- 7. Ayurvedic Recipes & Diet Therapy
-- ==========================================
CREATE TABLE IF NOT EXISTS ayurvedic_recipes (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    sanskrit_name VARCHAR(150),
    slug VARCHAR(150) NOT NULL UNIQUE,
    dosha_suitability VARCHAR(50) NOT NULL,
    meal_type VARCHAR(50),
    season_suitability VARCHAR(100),
    prep_time_minutes INTEGER,
    cook_time_minutes INTEGER,
    servings INTEGER,
    difficulty VARCHAR(30),
    description TEXT,
    therapeutic_benefits TEXT,
    digestive_action_agni TEXT,
    image_url VARCHAR(500),
    nutritional_highlight VARCHAR(255),
    classical_reference VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS recipe_ingredients (
    recipe_id BIGINT NOT NULL REFERENCES ayurvedic_recipes(id) ON DELETE CASCADE,
    ingredient TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS recipe_instructions (
    recipe_id BIGINT NOT NULL REFERENCES ayurvedic_recipes(id) ON DELETE CASCADE,
    instruction TEXT NOT NULL,
    step_order INTEGER NOT NULL,
    PRIMARY KEY (recipe_id, step_order)
);

-- ==========================================
-- 8. Prakriti Dosha Diagnostic Assessment Engine
-- ==========================================
CREATE TABLE IF NOT EXISTS dosha_quiz_questions (
    id BIGSERIAL PRIMARY KEY,
    category VARCHAR(100) NOT NULL,
    question_text TEXT NOT NULL,
    question_order INTEGER,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS dosha_quiz_options (
    id BIGSERIAL PRIMARY KEY,
    question_id BIGINT NOT NULL REFERENCES dosha_quiz_questions(id) ON DELETE CASCADE,
    option_text TEXT NOT NULL,
    dosha_type VARCHAR(30) NOT NULL,
    score_weight INTEGER DEFAULT 1,
    option_order INTEGER,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS dosha_assessment_results (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
    primary_dosha VARCHAR(30) NOT NULL,
    secondary_dosha VARCHAR(30),
    prakriti_constitution VARCHAR(100),
    vata_percentage NUMERIC(5, 2),
    pitta_percentage NUMERIC(5, 2),
    kapha_percentage NUMERIC(5, 2),
    dietary_recommendations TEXT,
    lifestyle_recommendations TEXT,
    recommended_herbs TEXT,
    recommended_yoga TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- ==========================================
-- Indexes for Enterprise Performance
-- ==========================================
CREATE INDEX IF NOT EXISTS idx_herbs_slug ON herbs(slug);
CREATE INDEX IF NOT EXISTS idx_herbs_botanical ON herbs(botanical_name);
CREATE INDEX IF NOT EXISTS idx_products_category ON products(category);
CREATE INDEX IF NOT EXISTS idx_products_dosha ON products(target_dosha);
CREATE INDEX IF NOT EXISTS idx_products_slug ON products(slug);
CREATE INDEX IF NOT EXISTS idx_orders_user ON orders(user_id);
CREATE INDEX IF NOT EXISTS idx_orders_number ON orders(order_number);
CREATE INDEX IF NOT EXISTS idx_recipes_dosha ON ayurvedic_recipes(dosha_suitability);
CREATE INDEX IF NOT EXISTS idx_recipes_slug ON ayurvedic_recipes(slug);
