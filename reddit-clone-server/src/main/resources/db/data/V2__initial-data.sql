-- Create users
INSERT INTO users (first_name, last_name, email, username, password, enabled, created_at, updated_at) VALUES
    ("Hemanth", "Reddy", "hemanth@email.com", "hemanth", "$2a$10$uZsrOtwFhQEQ3At/3CqZsOIRC/72RqrLuMXq.C4vJMhrc64D8ds9G", 1, NOW(), NOW()),
    ("Mahidhar", "Reddy", "mahidhar@email.com", "mahidhar", "$2a$10$X8.N6TfoQtgc6hZeAQIEue0QT5hUmkK/6DHpm9GoIO6MCIsR0C27i", 1, NOW(), NOW());

-- Create subreddits
INSERT INTO subreddits (name, title, description, user_id, created_at, updated_at) VALUES
    ("reactjs", "React JS Developer Community", "A group of React JS fanboys", 1, NOW() - INTERVAL 1 HOUR, NOW() - INTERVAL 1 HOUR),
    ("funny", "Everything Funny", "If you cant joke your life is a joke", 2, NOW() - INTERVAL 50 MINUTE, NOW() - INTERVAL 50 MINUTE),
    ("InsightfulQuestions", "Insightful Questions", "Questions that make you go 'Ohhh'", 1, NOW() - INTERVAL 40 MINUTE, NOW() - INTERVAL 40 MINUTE),
    ("oneliners", "Oneliners", "A variety of funny, one line jokes in a well-moderated, friendly community!", 1, NOW() - INTERVAL 30 MINUTE, NOW() - INTERVAL 30 MINUTE),
    ("readyplayerone", "Ready Player One", "Your nexus for all things Ready Player One", 2, NOW() - INTERVAL 20 MINUTE, NOW() - INTERVAL 20 MINUTE),
    ("reallyannoyingsounds", "Ahhhhhhhhhhhhhhhhh", "Ahhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh", 2, NOW() - INTERVAL 10 MINUTE, NOW() - INTERVAL 10 MINUTE);

-- Create posts
INSERT INTO posts (identifier, title, slug, body, user_id, subreddit_id, created_at, updated_at) VALUES
    ("rggenVY", "React 17 is out!!", "react_17_is_out", "But it has no new features...", 1, 1, NOW() - INTERVAL 20 MINUTE, NOW() - INTERVAL 20 MINUTE),
    ("00fOyPQ", "Comparing Redux to Vue composition API", "comparing_redux_to_vue_composition_api", "It feels like Redux is too much boilerplate", 2, 1, NOW() - INTERVAL 1 HOUR, NOW() - INTERVAL 1 HOUR),
    ("IvzYvbG", "What's your favourite React component library?", "whats_your_favourite_react_component_library", "(title) Mine is @material-ui", 1, 1, NOW() - INTERVAL 2 HOUR, NOW() - INTERVAL 2 HOUR),
    ("DnwJSvj", "What is the difference between healthy venting and shit talking?", "what_is_the_difference_between_healthy_venting_and_shit_talking", "What exactly does “talking behind your back” mean? When does giving an aggressive rant to a friend become disloyalty? Gossip?", 2, 3, NOW() - INTERVAL 40 MINUTE, NOW() - INTERVAL 40 MINUTE),
    ("ckVoiPf", "What was the most important social lesson you learned when you were younger?", "what_was_the_most_important_social_lesson_you_learned_when_you_were_younger", "I'm still relatively young, and I'm learning more and more about social situations and how people react to anything at all. I'd love to hear your advice and experiences that helped you.", 2, 3, NOW() - INTERVAL 3 HOUR, NOW() - INTERVAL 3 HOUR),
    ("tbkzHxF", "Why do cows never have any money? Because the farmers milk them dry!", "why_do_cows_never_have_any_money_because_the_farmers_milk_them_dry", null, 1, 2, NOW() - INTERVAL 9 DAY, NOW() - INTERVAL 9 DAY),
    ("CEPVxjR", "You would think that taking off a snail's shell would make it move faster, but it actually just makes it more sluggish.", "you_would_think_that_taking_off_a_snails_shell_would_make_it_move_faster_but_it_actually_just_makes_it_more_sluggish", null, 2, 2, NOW() - INTERVAL 10 DAY, NOW() - INTERVAL 10 DAY),
    ("FyztVqe", "I ate a clock yesterday, it was very time-consuming.", "i_ate_a_clock_yesterday_it_was_very_time_consuming", null, 1, 2, NOW() - INTERVAL 11 DAY, NOW() - INTERVAL 11 DAY),
    ("kk0GXky", "What’s the best thing about Switzerland?", "whats_the_best_thing_about_switzerland", "I don’t know, but the flag is a big plus.", 1, 2, NOW() - INTERVAL 5 DAY, NOW() - INTERVAL 5 DAY),
    ("aWbiMTf", "I invented a new word: Plagiarism!", "i_invented_a_new_word_plagiarism", null, 1, 2, NOW() - INTERVAL 4 DAY, NOW() - INTERVAL 4 DAY),
    ("eOMqOFS", "Did you hear about the mathematician who’s afraid of negative numbers?", "did_you_hear_about_the_mathematician_whos_afraid_of_negative_numbers", "He’ll stop at nothing to avoid them.", 2, 2, NOW() - INTERVAL 15 DAY, NOW() - INTERVAL 15 DAY),
    ("u1PZphn", "Helvetica and Times New Roman walk into a bar", "helvetica_and_times_new_roman_walk_into_a_bar", "“Get out of here!” shouts the bartender. “We don’t serve your type.”", 1, 2, NOW() - INTERVAL 16 DAY, NOW() - INTERVAL 16 DAY),
    ("HylUYd5", "Dove chocolate taste better than their soap.", "dove_chocolate_taste_better_than_their_soap", null, 1, 4, NOW() - INTERVAL '1 1' DAY_HOUR, NOW() - INTERVAL '1 1' DAY_HOUR),
    ("PwsDv25", "Raisin Awareness", "raisin_awareness", "I've been telling everyone about the benefits of eating dried grapes -- it's all about raisin awareness.", 2, 4, NOW() - INTERVAL '1 2' DAY_HOUR, NOW() - INTERVAL '1 2' DAY_HOUR),
    ("iqx3acA", "Iron Man and War Machine are cool, but there’s a stark difference between them.", "iron_man_and_war_machine_are_cool_but_theres_a_stark_difference_between_them", null, 1, 4, NOW() - INTERVAL '1 6' DAY_HOUR, NOW() - INTERVAL '1 6' DAY_HOUR),
    ("oCSW50J", "The adjective for metal is metallic, but not so for iron, which is ironic.", "the_adjective_for_metal_is_metallic_but_not_so_for_iron_which_is_ironic", null, 2, 4, NOW() - INTERVAL '1 8' DAY_HOUR, NOW() - INTERVAL '1 8' DAY_HOUR),
    ("saUwqdP", "What's the most difficult thing when you try to change your job?", "whats_the_most_difficult_thing_when_you_try_to_change_your_job", "This 2020 was a hard year for everyone, on June 2, my first son born.
I've too much stuff in my mind at this moment and one of them is finding a new job. My question is,
what's the most difficult thing when you try to change your job? I'm still in my comfort zone and I'm scared,
any advice to help me to take the step?", 1, 3, NOW() - INTERVAL 45 MINUTE, NOW() - INTERVAL 45 MINUTE);

-- Create comments
INSERT INTO comments (body, identifier, user_id, post_id, created_at, updated_at) VALUES
    ("That' punny hahaha!!", "wZ9m66zb", 1, 7, NOW() - INTERVAL '10 5' DAY_HOUR,  NOW() - INTERVAL '10 5' DAY_HOUR),
    ("Poor cows hahaha", "G9ntregv", 2, 7, NOW() - INTERVAL '10 3' DAY_HOUR, NOW() - INTERVAL '10 3' DAY_HOUR),
    ("To work even when I didnt have to!!", "JFd7haNZ", 1, 6, NOW() - INTERVAL '9 2' DAY_HOUR, NOW() - INTERVAL '9 2' DAY_HOUR),
    ("It's funny cuz it's true haha!", "VOLXaQzd", 1, 8, NOW() - INTERVAL '10 2' DAY_HOUR, NOW() - INTERVAL '10 2' DAY_HOUR),
    ("At least we're enjoying the milk I guess hihi", "MCqqWy8r", 2, 7, NOW() - INTERVAL '10 4' DAY_HOUR, NOW() - INTERVAL '10 4' DAY_HOUR),
    ("This is so bad, I dont know why im laughing Hahahaha!!", "VxnlwvEx", 2, 9, NOW() - INTERVAL '10 7' DAY_HOUR, NOW() - INTERVAL '10 7' DAY_HOUR);

-- Create votes
INSERT INTO votes (value, post_id, comment_id, user_id, created_at, updated_at) VALUES
    (1, 9, null, 1, NOW(), NOW()),
    (1, 9, null, 2, NOW(), NOW()),
    (1, 8, null, 2, NOW(), NOW()),
    (1, null, 1, 1, NOW(), NOW()),
    (1, null, 1, 2, NOW(), NOW()),
    (1, null, 2, 1, NOW(), NOW());
