ALTER TABLE order_items
  ALTER COLUMN context_modifier_id DROP NOT NULL,
  ALTER COLUMN item_id             DROP NOT NULL;

DO $$
BEGIN
  IF EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'fkioxxmi22ebf1r9vc70b0tedm3')
  THEN
    ALTER TABLE order_items DROP CONSTRAINT fkioxxmi22ebf1r9vc70b0tedm3;
  END IF;

  IF EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'fk88tn2oqcxl1034banqif9r70x')
  THEN
    ALTER TABLE order_items DROP CONSTRAINT fk88tn2oqcxl1034banqif9r70x;
  END IF;
END $$;

ALTER TABLE order_items
  ADD CONSTRAINT fk_order_items_context_modifier
      FOREIGN KEY (context_modifier_id)
      REFERENCES context_modifiers(id)
      ON DELETE SET NULL;

ALTER TABLE order_items
  ADD CONSTRAINT fk_order_items_item
      FOREIGN KEY (item_id)
      REFERENCES items(id)
      ON DELETE SET NULL;




ALTER TABLE order_item_options
  ALTER COLUMN context_modifier_id DROP NOT NULL,
  ALTER COLUMN option_id           DROP NOT NULL;

DO $$
BEGIN
  IF EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'fkqhlehuvktcu9duke9rvpvcyqy')
  THEN
    ALTER TABLE order_item_options DROP CONSTRAINT fkqhlehuvktcu9duke9rvpvcyqy;
  END IF;

  IF EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'fkb0i8d6fkc67ybqy3117w6m2e8')
  THEN
    ALTER TABLE order_item_options DROP CONSTRAINT fkb0i8d6fkc67ybqy3117w6m2e8;
  END IF;
END $$;

ALTER TABLE order_item_options
  ADD CONSTRAINT fk_oio_context_modifier
      FOREIGN KEY (context_modifier_id)
      REFERENCES context_modifiers(id)
      ON DELETE SET NULL;

ALTER TABLE order_item_options
  ADD CONSTRAINT fk_oio_option
      FOREIGN KEY (option_id)
      REFERENCES options(id)
      ON DELETE SET NULL;