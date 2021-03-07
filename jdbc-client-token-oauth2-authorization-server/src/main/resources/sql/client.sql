insert into oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types,
                                  web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity)
values ('oauth', 'oauth-resource', '$2a$10$EyohJlLTb7tx/TkVBOXc2eojIayx3Jz1IwUUNfi8CTKKWk5trzsoS', 'all',
        'authorization_code,password,refresh_token', 'https://www.google.com', 'ROLE_ADMIN,ROLE_USER', 6000, 6000);