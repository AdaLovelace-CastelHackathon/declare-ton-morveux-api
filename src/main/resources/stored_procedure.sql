CREATE FUNCTION reset_children_states()
 RETURNS SETOF RECORD AS $$
BEGIN
  RETURN QUERY UPDATE children SET is_sick = 'f' AND is_contagious = 'f' WHERE is_sick = 't';
END; $$
LANGUAGE 'plpgsql';