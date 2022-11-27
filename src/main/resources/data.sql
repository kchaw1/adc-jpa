insert into ox_accounts(account_id, account_name) values (1, '테스트 계정');
insert into ox_file_info(file_id, file_name) values (1, '테스트 파일1');

insert into ox_clients(client_id, client_name, file_id, account_id) values (1, '테스트 클라이언트', 1, 1);
insert into ox_clients(client_id, client_name, file_id, account_id) values (2, '테스트 클라이언트2', 1, 1);

insert into ox_campaigns(campaign_id, campaign_name, client_id) values (1, '테스트 캠페인1',1);
insert into ox_campaigns(campaign_id, campaign_name, client_id) values (2, '테스트 캠페인2',1);
insert into ox_campaigns(campaign_id, campaign_name, client_id) values (3, '테스트 캠페인3',1);

insert into ox_campaigns(campaign_id, campaign_name, client_id) values (4, '테스트 캠페인4',2);
insert into ox_campaigns(campaign_id, campaign_name, client_id) values (5, '테스트 캠페인5',2);

insert into ox_extra_feature(idx, context_id, context) values (1, 1, 'clients');

-- 다대다
insert into ox_banners(banner_id, banner_name) values (1, '배너1');
insert into ox_banners(banner_id, banner_name) values (2, '배너2');
insert into ox_banners(banner_id, banner_name) values (3, '배너3');

insert into ox_creatives(creative_id, creative_name) values (1, 'creative1');
insert into ox_creatives(creative_id, creative_name) values (2, 'creative2');
insert into ox_creatives(creative_id, creative_name) values (3, 'creative3');

insert into ox_banner_creatives(banner_id, creative_id) values (1, 1);
insert into ox_banner_creatives(banner_id, creative_id) values (1, 2);
insert into ox_banner_creatives(banner_id, creative_id) values (1, 3);
insert into ox_banner_creatives(banner_id, creative_id) values (2, 1);
insert into ox_banner_creatives(banner_id, creative_id) values (3, 1);
