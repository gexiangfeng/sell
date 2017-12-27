-- ��Ŀ
create table `product_category` (
    `category_id` int not null auto_increment,
    `category_name` varchar(64) not null comment '��Ŀ����',
    `category_type` int not null comment '��Ŀ���',
    `create_time` timestamp not null default current_timestamp comment '����ʱ��',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '�޸�ʱ��',
    primary key (`category_id`),
    unique key `uqe_category_type` (`category_type`)
);

-- ��Ʒ
create table `product_info` (
    `product_id` varchar(32) not null,
    `product_name` varchar(64) not null comment '��Ʒ����',
    `product_price` decimal(8,2) not null comment '����',
    `product_stock` int not null comment '���',
    `product_description` varchar(64) comment '����',
    `product_icon` varchar(512) comment 'Сͼ',
    `product_status` tinyint(3) DEFAULT '0' COMMENT '��Ʒ״̬,0����1�¼�',
    `category_type` int not null comment '��Ŀ���',
    `create_time` timestamp not null default current_timestamp comment '����ʱ��',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '�޸�ʱ��',
    primary key (`product_id`)
);

-- ����
create table `order_master` (
    `order_id` varchar(32) not null,
    `buyer_name` varchar(32) not null comment '�������',
    `buyer_phone` varchar(32) not null comment '��ҵ绰',
    `buyer_address` varchar(128) not null comment '��ҵ�ַ',
    `buyer_openid` varchar(64) not null comment '���΢��openid',
    `order_amount` decimal(8,2) not null comment '�����ܽ��',
    `order_status` tinyint(3) not null default '0' comment '����״̬, Ĭ��Ϊ���µ�',
    `pay_status` tinyint(3) not null default '0' comment '֧��״̬, Ĭ��δ֧��',
    `create_time` timestamp not null default current_timestamp comment '����ʱ��',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '�޸�ʱ��',
    primary key (`order_id`),
    key `idx_buyer_openid` (`buyer_openid`)
);

-- ������Ʒ
create table `order_detail` (
    `detail_id` varchar(32) not null,
    `order_id` varchar(32) not null,
    `product_id` varchar(32) not null,
    `product_name` varchar(64) not null comment '��Ʒ����',
    `product_price` decimal(8,2) not null comment '��ǰ�۸�,��λ��',
    `product_quantity` int not null comment '����',
    `product_icon` varchar(512) comment 'Сͼ',
    `create_time` timestamp not null default current_timestamp comment '����ʱ��',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '�޸�ʱ��',
    primary key (`detail_id`),
    key `idx_order_id` (`order_id`),
    foreign key(`order_id`) REFERENCES order_master(`order_id`)
);

-- ����(��¼��̨ʹ��, ���ҵ�¼֮�����ֱ�Ӳ���΢��ɨ���¼����ʹ���˺�����)
/**create table `seller_info` (
    `id` varchar(32) not null,
    `username` varchar(32) not null,
    `password` varchar(32) not null,
    `openid` varchar(64) not null comment '΢��openid',
    `create_time` timestamp not null default current_timestamp comment '����ʱ��',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '�޸�ʱ��',
    primary key (`id`)
) comment '������Ϣ��'; **/
create table `seller_info` (
    `id` varchar(32) not null,
    `username` varchar(32) not null,
    `password` varchar(32) not null,
    `openid` varchar(64) not null comment '΢��openid',
    `seller_id` varchar(32) not null,
     name varchar(32) not null,                
    description   varchar(32) not null,           
    delivery_time   int,    
    score       decimal(8,2),     
    service_score  decimal(8,2),         
    food_score    decimal(8,2),       
    rank_rate        decimal(8,2),
    min_price        decimal(8,2) ,
    delivery_price   decimal(8,2) ,
    rating_count      int,   
    sell_count        int,     
    bulletin        varchar(2000),  
    supports        varchar(2000),
    avatar         varchar(2000),
    pics         varchar(2000),  
    infos        varchar(2000),
    `create_time` timestamp not null default current_timestamp comment '����ʱ��',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '�޸�ʱ��',
    primary key (`id`)
) comment '������Ϣ��';

insert into sell.seller_info values(
1,
'abc',
'abc',
"oyYuNwtzmZZJDbneW1_PKV2o7UZ4",
"oyYuNwtzmZZJDbneW1_PKV2o7UZ4",
"��Ʒ�㷻�������ۣ�",
"����ר��",
38,
4.2,
4.1,
4.3,
69.2,
0,
0,
24,
90,
"��Ʒ�㷻��������ϵ��ط�Դ���й�ǧ��ŷ������ں��ִ��������գ���������⿴�ʦ�������������з������ش���Ȼ��0��ӵ�����Ʒ�������������������չ�����Ϊ���������Ʒ�ơ���2008����˻��2013��԰����ָ�����������̡�",
'[
      {
        "type": 0,
        "description": "����֧����28��5"
      },
      {
        "type": 1,
        "description": "VC���޳ȹ�֭ȫ��8��"
      },
      {
        "type": 2,
        "description": "���˾����ײ�"
      },
      {
        "type": 3,
        "description": "���̼�֧�ַ�Ʊ,���µ�д�÷�Ʊ̧ͷ"
      },
      {
        "type": 4,
        "description": "�Ѽ��롰���������ƻ�,ʳƷ��ȫ����"
      }
    ]',
'http://static.galileo.xiaojukeji.com/static/tms/seller_avatar_256px.jpg',
'[
      "http://fuss10.elemecdn.com/8/71/c5cf5715740998d5040dda6e66abfjpeg.jpeg?imageView2/1/w/180/h/180",
      "http://fuss10.elemecdn.com/b/6c/75bd250e5ba69868f3b1178afbda3jpeg.jpeg?imageView2/1/w/180/h/180",
      "http://fuss10.elemecdn.com/f/96/3d608c5811bc2d902fc9ab9a5baa7jpeg.jpeg?imageView2/1/w/180/h/180",
      "http://fuss10.elemecdn.com/6/ad/779f8620ff49f701cd4c58f6448b6jpeg.jpeg?imageView2/1/w/180/h/180"
    ]',
' [
      "���̼�֧�ַ�Ʊ,���µ�д�÷�Ʊ̧ͷ",
      "Ʒ��:������ϵ,�������",
      "�����в�ƽ�������������������ҵ���õ���B��102��Ԫ1340",
      "Ӫҵʱ��:10:00-20:30"
    ]',
    null,
    null
)