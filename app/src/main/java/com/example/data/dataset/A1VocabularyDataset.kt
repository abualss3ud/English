package com.example.data.dataset

import com.example.data.model.VocabularyWord

object A1VocabularyDataset {
    val words: List<VocabularyWord> = listOf(
        // Day 1: Greetings & Basics
        VocabularyWord(1, "Hello", "/həˈloʊ/", "مرحباً", "Interjection", "تحية", "Hello, how are you today?", "مرحباً، كيف حالك اليوم؟", "Greetings", "التحيات والأساسيات", 1),
        VocabularyWord(2, "Name", "/neɪm/", "اسم", "Noun", "اسم", "My name is Omar.", "اسمي عمر.", "Greetings", "التحيات والأساسيات", 1),
        VocabularyWord(3, "Please", "/pliːz/", "من فضلك / رجاءً", "Adverb", "ظرف / أدب", "Can you help me, please?", "هل يمكنك مساعدتي، من فضلك؟", "Greetings", "التحيات والأساسيات", 1),
        VocabularyWord(4, "Thank you", "/ˈθæŋk juː/", "شكراً لك", "Phrase", "عبارة", "Thank you for the coffee.", "شكراً لك على القهوة.", "Greetings", "التحيات والأساسيات", 1),
        VocabularyWord(5, "Goodbye", "/ˌɡʊdˈbaɪ/", "مع السلامة / وداعاً", "Interjection", "تحية وداع", "Goodbye, see you tomorrow!", "مع السلامة، أراك غداً!", "Greetings", "التحيات والأساسيات", 1),

        // Day 2: Food & Drinks
        VocabularyWord(6, "Water", "/ˈwɔː.tər/", "ماء", "Noun", "اسم", "I drink clean water.", "أنا أشرب ماءً نظيفاً.", "Food & Drink", "الطعام والمشروبات", 2),
        VocabularyWord(7, "Bread", "/bred/", "خبز", "Noun", "اسم", "We need fresh bread for breakfast.", "نحتاج إلى خبز طازج للإفطار.", "Food & Drink", "الطعام والمشروبات", 2),
        VocabularyWord(8, "Apple", "/ˈæp.əl/", "تفاحة", "Noun", "اسم", "I eat an apple every morning.", "أنا آكل تفاحة كل صباح.", "Food & Drink", "الطعام والمشروبات", 2),
        VocabularyWord(9, "Tea", "/tiː/", "شاي", "Noun", "اسم", "Do you like hot tea?", "هل تحب الشاي الساخن؟", "Food & Drink", "الطعام والمشروبات", 2),
        VocabularyWord(10, "Eat", "/iːt/", "يأكل", "Verb", "فعل", "They eat dinner together.", "هم يأكلون العشاء معاً.", "Food & Drink", "الطعام والمشروبات", 2),

        // Day 3: Family & People
        VocabularyWord(11, "Mother", "/ˈmʌð.ər/", "أم", "Noun", "اسم", "My mother is very kind.", "أمي لطيفة جداً.", "Family", "العائلة والناس", 3),
        VocabularyWord(12, "Father", "/ˈfɑː.ðər/", "أب", "Noun", "اسم", "His father works in a hospital.", "والده يعمل في مستشفى.", "Family", "العائلة والناس", 3),
        VocabularyWord(13, "Brother", "/ˈbrʌð.ər/", "أخ", "Noun", "اسم", "I play soccer with my brother.", "ألعب كرة القدم مع أخي.", "Family", "العائلة والناس", 3),
        VocabularyWord(14, "Sister", "/ˈsɪs.tər/", "أخت", "Noun", "اسم", "Her sister studies English.", "أختها تدرس اللغة الإنجليزية.", "Family", "العائلة والناس", 3),
        VocabularyWord(15, "Friend", "/frend/", "صديق", "Noun", "اسم", "Sami is my best friend.", "سامي هو أعز أصدقائي.", "Family", "العائلة والناس", 3),

        // Day 4: Daily Actions
        VocabularyWord(16, "Wake up", "/weɪk ʌp/", "يستيقظ", "Phrasal Verb", "فعل مركب", "I wake up at six in the morning.", "أستيقظ في السادسة صباحاً.", "Daily Routine", "الروتين اليومي", 4),
        VocabularyWord(17, "Sleep", "/sliːp/", "ينام", "Verb", "فعل", "Children need eight hours of sleep.", "يحتاج الأطفال إلى ثماني ساعات من النوم.", "Daily Routine", "الروتين اليومي", 4),
        VocabularyWord(18, "Work", "/wɜːrk/", "يعمل / عمل", "Verb/Noun", "فعل / اسم", "I work from Monday to Friday.", "أنا أعمل من الاثنين إلى الجمعة.", "Daily Routine", "الروتين اليومي", 4),
        VocabularyWord(19, "Read", "/riːd/", "يقرأ", "Verb", "فعل", "She likes to read books at night.", "هي تحب قراءة الكتب ليلاً.", "Daily Routine", "الروتين اليومي", 4),
        VocabularyWord(20, "Write", "/raɪt/", "يكتب", "Verb", "فعل", "Write your name on the paper.", "اكتب اسمك على الورقة.", "Daily Routine", "الروتين اليومي", 4),

        // Day 5: Home & Living
        VocabularyWord(21, "House", "/haʊs/", "منزل / بيت", "Noun", "اسم", "They live in a beautiful house.", "هم يعيشون في منزل جميل.", "Home", "المنزل والحياة", 5),
        VocabularyWord(22, "Door", "/dɔːr/", "باب", "Noun", "اسم", "Please close the door gently.", "من فضلك أغلق الباب بلطف.", "Home", "المنزل والحياة", 5),
        VocabularyWord(23, "Window", "/ˈwɪn.doʊ/", "نافذة / شباك", "Noun", "اسم", "Open the window for fresh air.", "افتح النافذة للحصول على هواء نقي.", "Home", "المنزل والحياة", 5),
        VocabularyWord(24, "Table", "/ˈteɪ.bəl/", "طاولة", "Noun", "اسم", "The book is on the wooden table.", "الكتاب على الطاولة الخشبية.", "Home", "المنزل والحياة", 5),
        VocabularyWord(25, "Room", "/ruːm/", "غرفة", "Noun", "اسم", "My room is quiet and tidy.", "غرفتي هادئة ومرتبة.", "Home", "المنزل والحياة", 5),

        // Day 6: City & Travel
        VocabularyWord(26, "Car", "/kɑːr/", "سيارة", "Noun", "اسم", "He drives a blue car.", "هو يقود سيارة زرقاء.", "Travel & City", "السفر والمدينة", 6),
        VocabularyWord(27, "Bus", "/bʌs/", "حافلة / باص", "Noun", "اسم", "The bus arrives at eight.", "تصل الحافلة عند الساعة الثامنة.", "Travel & City", "السفر والمدينة", 6),
        VocabularyWord(28, "Street", "/striːt/", "شارع", "Noun", "اسم", "Walk down this wide street.", "امشِ في هذا الشارع الواسع.", "Travel & City", "السفر والمدينة", 6),
        VocabularyWord(29, "Airport", "/ˈer.pɔːrt/", "مطار", "Noun", "اسم", "We arrived at the airport early.", "وصلنا إلى المطار مبكراً.", "Travel & City", "السفر والمدينة", 6),
        VocabularyWord(30, "Hotel", "/hoʊˈtel/", "فندق", "Noun", "اسم", "This hotel is close to the beach.", "هذا الفندق قريب من الشاطئ.", "Travel & City", "السفر والمدينة", 6),

        // Day 7: Common Adjectives
        VocabularyWord(31, "Big", "/bɪɡ/", "كبير", "Adjective", "صفة", "Cairo is a very big city.", "القاهرة مدينة كبيرة جداً.", "Adjectives", "الصفات الشائعة", 7),
        VocabularyWord(32, "Small", "/smɔːl/", "صغير", "Adjective", "صفة", "A small cat sits near the door.", "قطة صغيرة تجلس قرب الباب.", "Adjectives", "الصفات الشائعة", 7),
        VocabularyWord(33, "Good", "/ɡʊd/", "جيد / طيب", "Adjective", "صفة", "Have a good day!", "أتمنى لك يوماً جيداً!", "Adjectives", "الصفات الشائعة", 7),
        VocabularyWord(34, "New", "/nuː/", "جديد", "Adjective", "صفة", "I bought a new phone.", "اشتريت هاتفاً جديداً.", "Adjectives", "الصفات الشائعة", 7),
        VocabularyWord(35, "Happy", "/ˈhæp.i/", "سعيد", "Adjective", "صفة", "The children are very happy.", "الأطفال سعداء جداً.", "Adjectives", "الصفات الشائعة", 7),

        // Day 8: Numbers & Shopping
        VocabularyWord(36, "Money", "/ˈmʌn.i/", "نقود / مال", "Noun", "اسم", "I have some money in my bag.", "لدي بعض النقود في حقيبتي.", "Shopping", "التسوق والأموال", 8),
        VocabularyWord(37, "Price", "/praɪs/", "سعر / ثمن", "Noun", "اسم", "What is the price of this shirt?", "ما هو سعر هذا القميص؟", "Shopping", "التسوق والأموال", 8),
        VocabularyWord(38, "Buy", "/baɪ/", "يشتري", "Verb", "فعل", "I want to buy some fruit.", "أريد أن أشتري بعض الفاكهة.", "Shopping", "التسوق والأموال", 8),
        VocabularyWord(39, "Market", "/ˈmɑːr.kɪt/", "سوق", "Noun", "اسم", "The market is open on Friday.", "السوق مفتوح يوم الجمعة.", "Shopping", "التسوق والأموال", 8),
        VocabularyWord(40, "Cheap", "/tʃiːp/", "رخيص", "Adjective", "صفة", "Vegetables are cheap today.", "الخضروات رخيصة اليوم.", "Shopping", "التسوق والأموال", 8),

        // Day 9: Work & Study
        VocabularyWord(41, "School", "/skuːl/", "مدرسة", "Noun", "اسم", "Students walk to school together.", "الطلاب يمشون إلى المدرسة معاً.", "Study & Work", "الدراسة والعمل", 9),
        VocabularyWord(42, "Teacher", "/ˈtiː.tʃər/", "معلم / أستاذ", "Noun", "اسم", "Our teacher explains the lesson clearly.", "يشرح معلمنا الدرس بوضوح.", "Study & Work", "الدراسة والعمل", 9),
        VocabularyWord(43, "Book", "/bʊk/", "كتاب", "Noun", "اسم", "This book is very interesting.", "هذا الكتاب ممتع للغاية.", "Study & Work", "الدراسة والعمل", 9),
        VocabularyWord(44, "Pen", "/pen/", "قلم حبر", "Noun", "اسم", "Can I borrow your blue pen?", "هل يمكنني استعارة قلمك الأزرق؟", "Study & Work", "الدراسة والعمل", 9),
        VocabularyWord(45, "Learn", "/lɜːrn/", "يتعلم", "Verb", "فعل", "I want to learn English quickly.", "أريد أن أتعلم الإنجليزية بسرعة.", "Study & Work", "الدراسة والعمل", 9),

        // Day 10: Clothes & Colors
        VocabularyWord(46, "Shirt", "/ʃɜːrt/", "قميص", "Noun", "اسم", "He wears a white shirt.", "هو يرتدي قميصاً أبيض.", "Clothes", "الملابس والألوان", 10),
        VocabularyWord(47, "Shoes", "/ʃuːz/", "حذاء", "Noun", "اسم", "My new shoes are very comfortable.", "حذائي الجديد مريح جداً.", "Clothes", "الملابس والألوان", 10),
        VocabularyWord(48, "Red", "/red/", "أحمر", "Adjective/Noun", "صفة / اسم", "The red rose smells nice.", "الوردة الحمراء رائحتها جميلة.", "Clothes", "الملابس والألوان", 10),
        VocabularyWord(49, "Blue", "/bluː/", "أزرق", "Adjective/Noun", "صفة / اسم", "The sky is clear and blue.", "السماء صافية وزرقاء.", "Clothes", "الملابس والألوان", 10),
        VocabularyWord(50, "Wear", "/wer/", "يرتدي / يلبس", "Verb", "فعل", "Wear a jacket when it rains.", "ارتدِ سترة عندما تمطر.", "Clothes", "الملابس والألوان", 10),

        // Day 11: Weather & Nature
        VocabularyWord(51, "Sun", "/sʌn/", "شمس", "Noun", "اسم", "The sun shines warmly today.", "الشمس تشرق بدفء اليوم.", "Weather", "الطقس والطبيعة", 11),
        VocabularyWord(52, "Rain", "/reɪn/", "مطر", "Noun/Verb", "اسم / فعل", "Rain falls gently in winter.", "المطر يتساقط بلطف في الشتاء.", "Weather", "الطقس والطبيعة", 11),
        VocabularyWord(53, "Hot", "/hɑːt/", "حار / ساخن", "Adjective", "صفة", "Summer in Dubai is hot.", "الصيف في دبي حار.", "Weather", "الطقس والطبيعة", 11),
        VocabularyWord(54, "Cold", "/koʊld/", "بارد", "Adjective", "صفة", "The weather is cold tonight.", "الطقس بارد الليلة.", "Weather", "الطقس والطبيعة", 11),
        VocabularyWord(55, "Tree", "/triː/", "شجرة", "Noun", "اسم", "Birds sing in the tall tree.", "الطيور تغرد في الشجرة العالية.", "Weather", "الطقس والطبيعة", 11),

        // Day 12: Feelings & Health
        VocabularyWord(56, "Tired", "/taɪərd/", "تعبان / متعب", "Adjective", "صفة", "I am tired after a long day.", "أنا متعب بعد يوم طويل.", "Feelings", "المشاعر والصحة", 12),
        VocabularyWord(57, "Hungry", "/ˈhʌŋ.ɡri/", "جائع", "Adjective", "صفة", "Are you hungry for lunch?", "هل أنت جائع للغداء؟", "Feelings", "المشاعر والصحة", 12),
        VocabularyWord(58, "Thirsty", "/ˈθɜːr.sti/", "عطشان", "Adjective", "صفة", "Drink water when you feel thirsty.", "اشرب الماء عندما تشعر بالعطش.", "Feelings", "المشاعر والصحة", 12),
        VocabularyWord(59, "Doctor", "/ˈdɑːk.tər/", "طبيب / دكتور", "Noun", "اسم", "The doctor helped the patient.", "ساعد الطبيب المريض.", "Feelings", "المشاعر والصحة", 12),
        VocabularyWord(60, "Hospital", "/ˈhɑː.spɪ.təl/", "مستشفى", "Noun", "اسم", "The hospital is near the center.", "المستشفى بالقرب من المركز.", "Feelings", "المشاعر والصحة", 12),

        // Day 13: Time & Days
        VocabularyWord(61, "Today", "/təˈdeɪ/", "اليوم", "Adverb/Noun", "ظرف / اسم", "Today is a beautiful day.", "اليوم يوم جميل.", "Time", "الوقت والأيام", 13),
        VocabularyWord(62, "Tomorrow", "/təˈmɔːr.oʊ/", "غداً / بكره", "Adverb/Noun", "ظرف / اسم", "We will travel tomorrow.", "سوف نسافر غداً.", "Time", "الوقت والأيام", 13),
        VocabularyWord(63, "Time", "/taɪm/", "وقت / زمن", "Noun", "اسم", "What time does the train leave?", "في أي وقت يغادر القطار؟", "Time", "الوقت والأيام", 13),
        VocabularyWord(64, "Morning", "/ˈmɔːr.nɪŋ/", "صباح", "Noun", "اسم", "Good morning, everyone!", "صباح الخير للجميع!", "Time", "الوقت والأيام", 13),
        VocabularyWord(65, "Night", "/naɪt/", "ليل / مساء", "Noun", "اسم", "The stars are bright tonight.", "النجوم لامعة هذه الليلة.", "Time", "الوقت والأيام", 13),

        // Day 14: Movement & Directions
        VocabularyWord(66, "Go", "/ɡoʊ/", "يذهب", "Verb", "فعل", "Let us go to the park.", "دعنا نذهب إلى الحديقة.", "Movement", "الحركة والاتجاهات", 14),
        VocabularyWord(67, "Come", "/kʌm/", "يأتي", "Verb", "فعل", "Come here and sit down.", "تعال هنا واجلس.", "Movement", "الحركة والاتجاهات", 14),
        VocabularyWord(68, "Left", "/left/", "يسار / شمال", "Noun/Adjective", "اسم / صفة", "Turn left at the traffic light.", "انعطف يساراً عند إشارة المرور.", "Movement", "الحركة والاتجاهات", 14),
        VocabularyWord(69, "Right", "/raɪt/", "يمين / صحيح", "Noun/Adjective", "اسم / صفة", "The bank is on the right.", "المصرف على اليمين.", "Movement", "الحركة والاتجاهات", 14),
        VocabularyWord(70, "Stop", "/stɑːp/", "يتوقف / قف", "Verb/Noun", "فعل / اسم", "The taxi will stop here.", "سوف تتوقف سيارة الأجرة هنا.", "Movement", "الحركة والاتجاهات", 14),

        // Day 15: Food & Cooking
        VocabularyWord(71, "Milk", "/mɪlk/", "حليب / لبن", "Noun", "اسم", "Cold milk is healthy.", "الحليب البارد صحي.", "Food & Drink", "الطعام والمشروبات", 15),
        VocabularyWord(72, "Coffee", "/ˈkɑː.fi/", "قهوة", "Noun", "اسم", "I like black coffee with sugar.", "أحب القهوة السوداء مع السكر.", "Food & Drink", "الطعام والمشروبات", 15),
        VocabularyWord(73, "Cook", "/kʊk/", "يطبخ / طاهٍ", "Verb/Noun", "فعل / اسم", "My father cooks great rice.", "أبي يطبخ أرزاً رائعاً.", "Food & Drink", "الطعام والمشروبات", 15),
        VocabularyWord(74, "Fish", "/fɪʃ/", "سمك", "Noun", "اسم", "Fresh fish tastes delicious.", "السمك الطازج طعمه لذيذ.", "Food & Drink", "الطعام والمشروبات", 15),
        VocabularyWord(75, "Egg", "/eɡ/", "بيضة", "Noun", "اسم", "He eats two boiled eggs.", "هو يأكل بيضتين مسلوقتين.", "Food & Drink", "الطعام والمشروبات", 15),

        // Day 16: Technology & Communication
        VocabularyWord(76, "Phone", "/foʊn/", "هاتف", "Noun", "اسم", "My phone is on the charger.", "هاتفي على الشاحن.", "Technology", "التكنولوجيا والتواصل", 16),
        VocabularyWord(77, "Computer", "/kəmˈpjuː.tər/", "حاسوب / كمبيوتر", "Noun", "اسم", "She works on her computer.", "هي تعمل على حاسوبها.", "Technology", "التكنولوجيا والتواصل", 16),
        VocabularyWord(78, "Message", "/ˈmes.ɪdʒ/", "رسالة", "Noun", "اسم", "Send me a text message.", "أرسل لي رسالة نصية.", "Technology", "التكنولوجيا والتواصل", 16),
        VocabularyWord(79, "Call", "/kɔːl/", "يتصل / مكالمة", "Verb/Noun", "فعل / اسم", "I will call you in five minutes.", "سأتصل بك بعد خمس دقائق.", "Technology", "التكنولوجيا والتواصل", 16),
        VocabularyWord(80, "Internet", "/ˈɪn.tər.net/", "إنترنت", "Noun", "اسم", "The internet is fast here.", "الإنترنت سريع هنا.", "Technology", "التكنولوجيا والتواصل", 16),

        // Day 17: Essential Verbs
        VocabularyWord(81, "Help", "/help/", "يساعد / مساعدة", "Verb/Noun", "فعل / اسم", "Can you help me with this bag?", "هل يمكنك مساعدتي في هذه الحقيبة؟", "Verbs", "أفعال أساسية", 17),
        VocabularyWord(82, "Look", "/lʊk/", "ينظر", "Verb", "فعل", "Look at this nice picture.", "انظر إلى هذه الصورة الجميلة.", "Verbs", "أفعال أساسية", 17),
        VocabularyWord(83, "Listen", "/ˈlɪs.ən/", "يستمع", "Verb", "فعل", "Listen carefully to the speaker.", "استمع بعناية إلى المتحدث.", "Verbs", "أفعال أساسية", 17),
        VocabularyWord(84, "Speak", "/spiːk/", "يتكلم / يتحدث", "Verb", "فعل", "I speak Arabic and English.", "أنا أتحدث العربية والإنجليزية.", "Verbs", "أفعال أساسية", 17),
        VocabularyWord(85, "Understand", "/ˌʌn.dərˈstænd/", "يفهم", "Verb", "فعل", "Do you understand the question?", "هل تفهم السؤال؟", "Verbs", "أفعال أساسية", 17),

        // Day 18: Places & Surroundings
        VocabularyWord(86, "City", "/ˈsɪt.i/", "مدينة", "Noun", "اسم", "Riyadh is a modern city.", "الرياض مدينة عصرية.", "Places", "الأماكن والمحيط", 18),
        VocabularyWord(87, "Park", "/pɑːrk/", "حديقة / منتزه", "Noun", "اسم", "Families relax in the park.", "العائلات تسترخي في الحديقة.", "Places", "الأماكن والمحيط", 18),
        VocabularyWord(88, "Shop", "/ʃɑːp/", "محل / دكان", "Noun", "اسم", "The grocery shop is nearby.", "محل البقالة قريب.", "Places", "الأماكن والمحيط", 18),
        VocabularyWord(89, "Bank", "/bæŋk/", "مصرف / بنك", "Noun", "اسم", "The bank closes at four.", "يغلق البنك في الرابعة.", "Places", "الأماكن والمحيط", 18),
        VocabularyWord(90, "Restaurant", "/ˈres.trɑːnt/", "مطعم", "Noun", "اسم", "We had dinner in an Italian restaurant.", "تناولنا العشاء في مطعم إيطالي.", "Places", "الأماكن والمحيط", 18),

        // Day 19: Travel & Luggage
        VocabularyWord(91, "Ticket", "/ˈtɪk.ɪt/", "تذكرة", "Noun", "اسم", "Keep your flight ticket safe.", "احتفظ بتذكرة طيرانك بأمان.", "Travel & City", "السفر والمدينة", 19),
        VocabularyWord(92, "Passport", "/ˈpæs.pɔːrt/", "جواز سفر", "Noun", "اسم", "Show your passport to the officer.", "أظهر جواز سفرك للموظف.", "Travel & City", "السفر والمدينة", 19),
        VocabularyWord(93, "Bag", "/bæɡ/", "حقيبة / شنطة", "Noun", "اسم", "Put your laptop inside the bag.", "ضع حاسوبك المحمول داخل الحقيبة.", "Travel & City", "السفر والمدينة", 19),
        VocabularyWord(94, "Train", "/treɪn/", "قطار", "Noun", "اسم", "The fast train leaves soon.", "القطار السريع يغادر قريباً.", "Travel & City", "السفر والمدينة", 19),
        VocabularyWord(95, "Map", "/mæp/", "خريطة", "Noun", "اسم", "Check the tourist map for directions.", "تحقق من الخريطة السياحية لمعرفة الاتجاهات.", "Travel & City", "السفر والمدينة", 19),

        // Day 20: Useful Descriptions
        VocabularyWord(96, "Fast", "/fæst/", "سريع", "Adjective/Adverb", "صفة / ظرف", "Cheetahs run very fast.", "الفهود تركض بسرعة كبيرة.", "Adjectives", "الصفات الشائعة", 20),
        VocabularyWord(97, "Slow", "/sloʊ/", "بطيء", "Adjective", "صفة", "Traffic is slow this evening.", "حركة المرور بطيئة هذا المساء.", "Adjectives", "الصفات الشائعة", 20),
        VocabularyWord(98, "Easy", "/ˈiː.zi/", "سهل / يسير", "Adjective", "صفة", "Learning English is easy with practice.", "تعلم الإنجليزية سهل مع الممارسة.", "Adjectives", "الصفات الشائعة", 20),
        VocabularyWord(99, "Clean", "/kliːn/", "نظيف", "Adjective/Verb", "صفة / فعل", "Keep your workspace clean.", "حافظ على نظافة مكان عملك.", "Adjectives", "الصفات الشائعة", 20),
        VocabularyWord(100, "Ready", "/ˈred.i/", "مستعد / جاهز", "Adjective", "صفة", "Are you ready for today's lesson?", "هل أنت مستعد لدرس اليوم؟", "Adjectives", "الصفات الشائعة", 20),

        // Day 21: Daily Expressions
        VocabularyWord(101, "Excuse me", "/ɪkˈskjuːz miː/", "عفواً / معذرة", "Phrase", "عبارة", "Excuse me, where is the station?", "عفواً، أين المحطة؟", "Greetings", "التحيات والأساسيات", 21),
        VocabularyWord(102, "Welcome", "/ˈwel.kəm/", "أهلاً وسهلاً", "Phrase/Verb", "عبارة / ترحيب", "You are always welcome here.", "أهلاً وسهلاً بك دائماً هنا.", "Greetings", "التحيات والأساسيات", 21),
        VocabularyWord(103, "See you", "/siː juː/", "أراك لاحقاً", "Phrase", "عبارة", "See you on Sunday!", "أراك يوم الأحد!", "Greetings", "التحيات والأساسيات", 21),
        VocabularyWord(104, "Great", "/ɡreɪt/", "رائع / عظيم", "Adjective", "صفة", "You did a great job today!", "لقد قمت بعمل رائع اليوم!", "Adjectives", "الصفات الشائعة", 21),
        VocabularyWord(105, "Together", "/təˈɡeð.ər/", "معاً / سوياً", "Adverb", "ظرف", "We practice English together.", "نحن نمارس الإنجليزية معاً.", "Daily Routine", "الروتين اليومي", 21)
    )

    fun getTopics(): List<String> = words.map { it.topicArabic }.distinct()
}
