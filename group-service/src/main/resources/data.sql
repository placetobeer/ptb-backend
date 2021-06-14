INSERT INTO friend_group (name) VALUES
('Bratis Kartoffeln'),
('Hüttengaudis'),
('Coffin Dancer'),
('Karaoke Kollegium'),
('Ultimate FrisBees');


INSERT INTO user (name, email) VALUES
('Bea','bea@mail.com'),
('Jonas','jonas@mail.com'),
('Katja','katja@mail.com'),
('Patrick','patrick@mail.com'),
('Lucie','lucie@mail.com'),
('Tom','tom@mail.com'),
('Nana Otafrija','nOtafija@mail.com'),
('Pall-Bearing','pall-B@mail.com'),
('Waiting Service','waiting-service@mail.com'),
('Dada awu','dada.awu@mail.com');

INSERT INTO membership (group_id, member_id, role) VALUES
('1', '1', '2'),
('1', '2', '1'),
('1', '3', '1'),
('1', '4', '1'),
('1', '5', '0'),
('1', '6', '0'),
('2', '4', '2'),
('2', '5', '1'),
('2', '6', '1'),
('3', '7', '2'),
('3', '8', '1'),
('3', '9', '0'),
('3', '10', '0'),
('3', '4', '0'),
('4', '1', '1'),
('4', '5', '2'),
('4', '6', '0'),
('5', '3', '0'),
('5', '5', '2');

INSERT INTO invitation (recipient_email, recipient_id, emitter_id, group_id, role) VALUES
('nOtafija@mail.com', 7, 1, 1, 1),
('coolman.mccool@mail.com', null, 1, 1, 0),
('tom@mail.com','4','6','4','1'),
('katja@mail.com','4','3','5','0');
